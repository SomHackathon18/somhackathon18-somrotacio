import sqlite3


class Persistence:
    def __init__(self, path, log):
        self.path = path
        self.log = log

    def init_db(self):
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute('''CREATE TABLE IF NOT EXISTS parking
                         (vehicle TEXT,
                         parkingArea INT,
                         startTime TIMESTAMP,
                         endTime TIMESTAMP,
                         PRIMARY KEY (vehicle, parkingArea, startTime)
                         )''')
            db_conn.commit()
        except Exception as e:
            self.close_connection(db_conn)
            raise e
        self.close_connection(db_conn)

    def create_connection(self):
        try:
            db_conn = sqlite3.connect(self.path)
            db_client = db_conn.cursor()
            return db_conn, db_client
        except Exception as ex:
            self.log.error('Error connecting with the SQLite DB. Exception: ' + str(ex))
            raise ex

    def close_connection(self, db_conn):
        try:
            db_conn.close()
            return db_conn
        except Exception as ex:
            self.log.error('Error closing the connection with the SQLite DB. Exception: ' + str(ex))
            raise ex

    def create(self, vehicle, parkingArea, startTime):
        sql_script = 'INSERT INTO parking (vehicle, parkingArea, startTime) VALUES (:vehicle, :parkingArea, :startTime)'
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute(sql_script, {'vehicle': vehicle, 'parkingArea': parkingArea, 'startTime': startTime})
            db_conn.commit()
            self.close_connection(db_conn)
        except Exception as ex:
            self.log.error('Error executing a query in the SQLite DB. Exception: ' + str(ex))
            if db_conn:
                self.close_connection(db_conn)
            raise ex

    def update(self, vehicle, parkingArea, startTime, endTime):
        sql_script = 'UPDATE parking SET endTime = :endTime WHERE vehicle = :vehicle AND parkingArea = :parkingArea AND startTime = :startTime'
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute(sql_script, {'vehicle': vehicle, 'parkingArea': parkingArea, 'startTime': startTime, 'endTime': endTime})
            db_conn.commit()
            self.close_connection(db_conn)
        except Exception as ex:
            self.log.error('Error executing a query in the SQLite DB. Exception: ' + str(ex))
            if db_conn:
                self.close_connection(db_conn)
            raise ex