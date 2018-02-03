from flask import Flask, request, logging

from persistence import Persistence

app = Flask(__name__)


@app.route('/')
def hello():
    return "Hello World!"


def validate_parking(parking):
    pass


@app.route('/parking', methods=['POST'])
def parking_post():
    parking = request.get_json()
    validate_parking(parking)
    repository.create(parking['vehicle'], parking['parkingArea'], parking['startTime'])
    return parking, 201


@app.route('/parking', methods=['GET'])
def parking_list():
    parking_ll = repository.list()
    return parking_ll


if __name__ == '__main__':
    repository = Persistence('database.db', logging.getLogger(__name__))
    repository.init_db()

    app.run()
