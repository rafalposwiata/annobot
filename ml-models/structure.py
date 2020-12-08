import enum


class Record:

    def __init__(self, record):
        self.text = record["text"]
        self.label = record["label"]


class Data:

    def __init__(self, data):
        self.records = [Record(r) for r in data["records"]]


class OutputType(enum.Enum):
    SINGLE_LABEL = 'SINGLE_LABEL'
    MULTI_LABEL = 'MULTI_LABEL'
    NUMBER = 'NUMBER'
    TEXT = 'TEXT'

    def __str__(self):
        return str(self.value)


class OutputInfo:

    def __init__(self, type, possible_labels=None):
        self.type: str = str(type)
        self.possible_labels = possible_labels


class ModelInfo:

    def __init__(self, name: str, output_info: OutputInfo):
        self.name = name
        self.output_info = output_info
