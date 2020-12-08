from flask import Flask, jsonify, request, Response
import json
import jsonpickle

from structure import Data
from models import get_available_models

app = Flask(__name__)
available_models = get_available_models()


@app.route('/update/<model_name>', methods=["POST"])
def update(model_name):
    if request.method == 'POST':
        data = Data(json.loads(request.data.decode("utf-8")))
        model = available_models.get(model_name)
        model.fit(data)

    return jsonify({'result': 'model is training'})


@app.route('/predict/<model_name>', methods=["POST"])
def predict(model_name):
    if request.method == 'POST':
        text = json.loads(request.data.decode("utf-8"))['text']
        model = available_models.get(model_name)
        prediction = model.predict(text)

    return jsonify({'result': prediction})


@app.route('/models', methods=["GET"])
def models():
    models_infos = {"models": [model.get_model_info() for model in available_models.values()]}
    return Response(jsonpickle.encode(models_infos, unpicklable=False), mimetype='application/json')


if __name__ == "__main__":
    print("* Starting web server... please wait until server has fully started")
    # app.run(host='0.0.0.0', threaded=False)
    app.run()
