import os
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


@app.route('/parking', methods=['POST'])
def parking_post():
    parking = request.get_json()
    validate_parking(parking)
    parking = repository.create(parking['vehicle'], parking['parkingArea'], parking['startTime'])
    return jsonify(parking), 201

@app.route('/parkings', methods=['GET'])
def parking_list():
    parking_ll = repository.list()
    print parking_ll
    return jsonify(parking_ll)


@app.route('/cid', methods=['GET'])
def cid_list():
    with open('cid.json', 'r') as json:
        return Response(json.read(), status=200, mimetype='application/json')


if __name__ == '__main__':
    repository = Persistence(os.environ['DB_PATH'], logging.getLogger(__name__))
    repository.init_db()

    app.run(host=os.environ['IP_LISTEN'], port=int(os.environ['PORT_LISTEN']))
