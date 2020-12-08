import numpy as np
from flair.data import Sentence
from flair.embeddings import WordEmbeddings

from sklearn import svm

from structure import ModelInfo, OutputType, OutputInfo, Data


def words_vectors_to_sentence_vector(words):
    return np.mean(words, axis=0) + np.max(words, axis=0)


class SVMModel:

    def __init__(self):
        print('Creating new model')
        self._name: str = 'SVM_binary'
        self._output_type: OutputType = OutputType.SINGLE_LABEL
        self._labels = ['positive', 'negative']
        self._embedding = WordEmbeddings('glove')
        self._model = self._load_model()

    def get_model_info(self) -> ModelInfo:
        return ModelInfo(self._name, OutputInfo(self._output_type, self._labels))

    def _load_model(self):
        # TODO loading model from file
        return svm.SVC()

    def fit(self, data: Data):
        X, y = [], []
        for record in data.records:
            X.append(self._text_to_vector(record.text))
            y.append(self._labels.index(record.label))
        self._model.fit(X, y)

    def predict(self, text):
        vector = self._text_to_vector(text)
        pred = self._model.predict([vector])[0]
        return self._labels[pred]

    def _text_to_vector(self, text):
        sentence = Sentence(text)
        self._embedding.embed(sentence)
        return words_vectors_to_sentence_vector([token.embedding.cpu().numpy() for token in sentence])


def get_available_models():
    return {
        'SVM_binary': SVMModel()
    }
