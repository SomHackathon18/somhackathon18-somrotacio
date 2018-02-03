import os

import datetime
from logging import basicConfig
from logging import INFO

from flask import Flask, request, logging, Response, jsonify
from flask_cors import CORS

from persistence import Persistence

app = Flask(__name__)
CORS(app)


@app.route('/')
def hello():
    log = read_file(os.environ['APP_LOG'])
    return log


def read_file(file_path):
    abs_path = os.path.dirname(os.path.abspath(__file__))
    log = os.path.join(abs_path, file_path)
    with open(log, 'r') as logfile:
        content = logfile.read()
    return content


def validate_parking(parking):
    pass


@app.route('/parkings', methods=['POST'])
def parking_post():
    parking = request.get_json()
    validate_parking(parking)
    start_time = datetime.datetime.now()
    parking = repository.create(parking['vehicle'], parking['parkingArea'], start_time)
    return jsonify(parking), 201


@app.route('/parkings/<parkingid>/endtime', methods=['POST'])
def parking_finish(parkingid):
    end_time = datetime.datetime.now()
    parking = repository.update(parkingid, end_time)
    return jsonify(parking), 201


@app.route('/parkings', methods=['GET'])
def parking_list():
    parking_ll = repository.list()
    print parking_ll
    return jsonify(parking_ll)


@app.route('/cid', methods=['GET'])
def cid_list():
    cid = read_file('cid.json')
    return Response(cid, status=200, mimetype='application/json')


if __name__ == '__main__':
    basicConfig(filename=os.environ['APP_LOG'], level=INFO)

    repository = Persistence(os.environ['DB_PATH'], logging.getLogger(__name__))
    repository.init_db()

    app.run(host=os.environ['IP_LISTEN'], port=int(os.environ['PORT_LISTEN']), threaded=True)
