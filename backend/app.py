import os

import datetime
from flask import Flask, request, logging, Response, jsonify
from flask_cors import CORS

from persistence import Persistence

app = Flask(__name__)
CORS(app)


@app.route('/')
def hello():
    return "Hello World!"


def validate_parking(parking):
    pass


@app.route('/parkings', methods=['POST'])
def parking_post():
    parking = request.get_json()
    validate_parking(parking)
    startTime = datetime.datetime.now()
    parking = repository.create(parking['vehicle'], parking['parkingArea'], startTime)
    return jsonify(parking), 201

@app.route('/parkings/<parkingid>/endtime', methods=['POST'])
def parking_finish(parkingid):
    endTime = datetime.datetime.now()
    parking = repository.update(parkingid, endTime)
    return jsonify(parking), 201

@app.route('/parkings', methods=['GET'])
def parking_list():
    parking_ll = repository.list()
    print parking_ll
    return jsonify(parking_ll)


@app.route('/cid', methods=['GET'])
def cid_list():
    THIS_FOLDER = os.path.dirname(os.path.abspath(__file__))
    cid_json = os.path.join(THIS_FOLDER, 'cid.json')
    with open(cid_json, 'r') as json:
        content = json.read()
    return Response(content, status=200, mimetype='application/json')


if __name__ == '__main__':
    repository = Persistence(os.environ['DB_PATH'], logging.getLogger(__name__))
    repository.init_db()

    app.run(host=os.environ['IP_LISTEN'], port=int(os.environ['PORT_LISTEN']), threaded=True)
