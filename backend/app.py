import os

import datetime
from collections import OrderedDict
from logging import basicConfig
from logging import INFO

from flask import Flask, request, logging, Response, jsonify, json
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
    parking = repository.create(parking['vehicle'], parking['parkingArea'], start_time, parking['tipusVehicle'])
    return jsonify(parking), 201


@app.route('/parkings/recent', methods=['GET'])
def recent_parking():
    recent = repository.list_occupied_ordered()
    return jsonify(recent), 200


@app.route('/parkings/<parkingid>/endtime', methods=['POST'])
def parking_finish(parkingid):
    end_time = datetime.datetime.now()
    repository.update(parkingid, end_time)
    return "{}"


@app.route('/parkings', methods=['GET'])
def parking_list():
    parking_ll = repository.list()
    return jsonify(parking_ll)


@app.route('/cid', methods=['GET'])
def cid_list():
    from cid_infostat import cid as cids
    cid_dict = {}
    for cid in cids:
        cid['nplacesocupades'] = 0
        cid['nplacesocupadesperveh'] = [0] * 3
        cid_dict[cid["ID"]] = cid

    parking_list = repository.list_occupied()
    for parking in parking_list:
        cid = cid_dict[parking['parkingArea']]
        tipusVehicle = parking['tipusVehicle']
        cid['nplacesocupades'] = cid['nplacesocupades'] + 1
        cid['nplacesocupadesperveh'][tipusVehicle] = cid['nplacesocupadesperveh'][tipusVehicle] + 1

    return jsonify(cid_dict)


if __name__ == '__main__':
    abs_path = os.path.dirname(os.path.abspath(__file__))
    log = os.path.join(abs_path, os.environ['APP_LOG'])
    basicConfig(filename=log, level=INFO)

    db = os.path.join(abs_path, os.environ['DB_PATH'])
    repository = Persistence(db, logging.getLogger(__name__))
    repository.init_db()

    app.run(host=os.environ['IP_LISTEN'], port=int(os.environ['PORT_LISTEN']), threaded=True)
