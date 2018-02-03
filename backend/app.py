from flask import Flask, request

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
    return parking, 201


if __name__ == '__main__':
    app.run()
