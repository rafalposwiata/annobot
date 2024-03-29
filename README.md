# Annobot

The Annobot platform is the first open-sourced platform for annotating and creating datasets through conversation with a chatbot [[paper](https://aclanthology.org/2020.coling-demos.14/)].

### !!! The system is in the prototype phase. Some functionalities are not fully implemented. Full version of the platform with more detailed information and instructions will be made available as soon as possible !!!

If you use the code from this repository, please cite:

```bib
@inproceedings{poswiata-perelkiewicz-2020-annobot,
    title = "Annobot: Platform for Annotating and Creating Datasets through Conversation with a Chatbot",
    author = "Po{\'s}wiata, Rafa{\l} and Pere{\l}kiewicz, Micha{\l}",
    booktitle = "Proceedings of the 28th International Conference on Computational Linguistics: System Demonstrations",
    month = dec,
    year = "2020",
    address = "Barcelona, Spain (Online)",
    publisher = "International Committee on Computational Linguistics (ICCL)",
    url = "https://aclanthology.org/2020.coling-demos.14",
    doi = "10.18653/v1/2020.coling-demos.14",
    pages = "75--79"
}
```


### 1. Functionalities

- Annotating through conversation with a chatbot.
- Pre-annotation.
- Active sampling.
- Online learning.
- Real-time inter-annotator agreement.
- Integration with Facebook Messenger.

### 2. Demo

https://annobot.herokuapp.com/#/chat/Bob

### 3. Architecture
![Annobot architecture](img/annobot_architecture.png)

### 4. Annobot screenshots
![Bot configuration](img/configuration.png)

![Annobot chat](img/annobot_chat.png)

![Facebbok Messenger](img/facebook.png)

### 5. Installation

#### 5.1 Database

The first step is to install PostgreSQL database, which can be downloaded [here](https://www.postgresql.org/download/). Then you have to run the scripts that create the schemes and tables available in the [database/schema](database/schema) directory.

#### 5.2 Configuration
In this step you need to modify [application.json](app-monolith/src/main/resources/application.yml) file by entering the address of the created database and the database user's login/password.

#### 5.3 Running Annobot server
In main directory run mvn clean package and then you can run main class [AnnobotApplication](app-monolith/src/main/java/com/annobot/AnnobotApplication.java).

#### 5.4 Running Annobot admin panel and Annobot chat
Go to [client](client) directory and run npm install and npm start.
