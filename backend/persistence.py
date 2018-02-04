import sqlite3
import json
from collections import OrderedDict

import uuid as uuid


class Persistence:
    def __init__(self, path, log):
        self.path = path
        self.log = log

    def init_db(self):
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute('''CREATE TABLE IF NOT EXISTS parking
                         (id INTEGER PRIMARY KEY AUTOINCREMENT,
                         vehicle TEXT,
                         parkingArea INTEGER,
                         startTime TIMESTAMP,
                         endTime TIMESTAMP,
                         tipusVehicle INTEGER,
                         CONSTRAINT unique_parking UNIQUE (vehicle, parkingArea, startTime)
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

    def create(self, vehicle, parkingArea, startTime, tipusVehicle):
        sql_script = 'INSERT INTO parking (vehicle, parkingArea, startTime, tipusVehicle) VALUES (:vehicle, :parkingArea, :startTime, :tipusVehicle)'
        db_conn, db_client = self.create_connection()
        try:
            cursor = db_conn.cursor()
            parking = {'vehicle': vehicle, 'parkingArea': parkingArea, 'startTime': startTime, 'tipusVehicle': tipusVehicle}
            cursor.execute(sql_script, parking)
            parkingid = cursor.lastrowid
            db_conn.commit()
            self.close_connection(db_conn)
            parking['id'] = parkingid
            return parking
        except Exception as ex:
            self.log.error('Error executing a query in the SQLite DB. Exception: ' + str(ex))
            if db_conn:
                self.close_connection(db_conn)
            raise ex

    def update(self, parkingid, endTime):
        sql_script = 'UPDATE parking SET endTime = :endTime WHERE id = :parkingid'
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute(sql_script, {'parkingid': parkingid, 'endTime': endTime})
            db_conn.commit()
            self.close_connection(db_conn)
        except Exception as ex:
            self.log.error('Error executing a query in the SQLite DB. Exception: ' + str(ex))
            if db_conn:
                self.close_connection(db_conn)
            raise ex

    def exist(self, vehicle, parkingArea, startTime):
        sql_script = 'SELECT * FROM parking WHERE vehicle = :vehicle AND parkingArea = :parkingArea AND startTime = :startTime'
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute(sql_script, {'vehicle': vehicle, 'parkingArea': parkingArea, 'startTime': startTime})
            row = db_client.fetchone()
            self.close_connection(db_conn)
            return row is not None
        except Exception as ex:
            self.log.error('Error executing a query in the SQLite DB. Exception: ' + str(ex))
            if db_conn:
                self.close_connection(db_conn)
            raise ex

    def get(self, vehicle, parkingArea, startTime):
        sql_script = 'SELECT vehicle, parkingArea, startTime, endTime, tipusVehicle FROM parking WHERE vehicle = :vehicle AND parkingArea = :parkingArea AND startTime = :startTime'
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute(sql_script, {'vehicle': vehicle, 'parkingArea': parkingArea, 'startTime': startTime})
            parking = db_client.fetchone()[0]
            self.close_connection(db_conn)
            return parking
        except Exception as ex:
            self.log.error('Error executing a query in the SQLite DB. Exception: ' + str(ex))
            if db_conn:
                self.close_connection(db_conn)
            raise ex

    def delete(self, vehicle, parkingArea, startTime):
        sql_script = "DELETE FROM parking WHERE vehicle = :vehicle AND parkingArea = :parkingArea AND startTime = :startTime"
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

    def list(self):
        sql_script = "SELECT * FROM parking"
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute(sql_script)
            keys = [description[0] for description in db_client.description]
            rows = db_client.fetchall()
            parking_list = []
            for row in rows:
                parking_row = OrderedDict(zip(keys, row))
                parking_list.append(parking_row)
            self.close_connection(db_conn)
            return parking_list
        except Exception as ex:
            self.log.error('Error executing a query in the SQLite DB. Exception: ' + str(ex))
            if db_conn:
                self.close_connection(db_conn)
            raise ex

    def list_occupied(self):
        sql_script = "SELECT * FROM parking WHERE endTime IS NULL"
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute(sql_script)
            keys = [description[0] for description in db_client.description]
            rows = db_client.fetchall()
            parking_list = []
            for row in rows:
                parking_row = OrderedDict(zip(keys, row))
                parking_list.append(parking_row)
            self.close_connection(db_conn)
            return parking_list
        except Exception as ex:
            self.log.error('Error executing a query in the SQLite DB. Exception: ' + str(ex))
            if db_conn:
                self.close_connection(db_conn)
            raise ex

    def list_occupied_ordered(self):
        sql_script = "SELECT * FROM parking WHERE endTime IS NULL ORDER BY startTime"
        db_conn, db_client = self.create_connection()
        try:
            db_client.execute(sql_script)
            keys = [description[0] for description in db_client.description]
            rows = db_client.fetchall()
            parking_list = []
            for row in rows:
                parking_row = OrderedDict(zip(keys, row))
                parking_list.append(parking_row)
            self.close_connection(db_conn)
            return parking_list
        except Exception as ex:
            self.log.error('Error executing a query in the SQLite DB. Exception: ' + str(ex))
            if db_conn:
                self.close_connection(db_conn)
            raise ex