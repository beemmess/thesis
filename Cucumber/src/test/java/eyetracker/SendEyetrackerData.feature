# Created by beemmess at 02/11/2018
Feature: Send a raw Eyetracking data to Feature extraction server


  Scenario: As a user I want the eyetracking data to be successfully sent to the server
    Given That the eyetracking data of the user is collected and sent as a JSON string to the server:
    """
    {
    "type" : "raw",
    "id": "Cucumber",
    "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR",
    "data": "6682.1,0.91,0.92,0.93,0.94,2.2,2.4\n6682.2,nan,nan,0.83,0.84,3.2,3.4\n6682.3,0.93,0.94,0.83,0.84,3.3,3.5\n"
    }
    """
    When The raw data is sent to the server "http://142.93.109.50:8080/FeatureExtractionServer/api/eyetracker"
    Then The data is succesfully sent and the server code response should be <200>

  Scenario: As a user, I mistakenly send a badly formatted eyetracking data to the server
    Given That the eyetracking data of the user is collected and sent as a JSON string to the server:
    """
    {
    "id": "Cucumber"
    "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR"
    "data": "6682.1,0.91,0.92,0.93,0.94,2.2,2.4\n6682.2,nan,nan,0.83,0.84,3.2,3.4\n6682.3,0.93,0.94,0.83,0.84,3.3,3.5\n"
    """
    When The raw data is sent to the server "http://142.93.109.50:8080/FeatureExtractionServer/api/eyetracker"
    Then The raw data is unsuccesfully sent to the server and respond code is <400>


  Scenario Outline: As a researcher, I want to be sure that the data is processed correctly
    Given That the eyetracking data of the user has been sent to the server as a JSON:
    """
    {
    "type" : "raw",
    "id": "Cucumber",
    "features": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR",
    "data": "6682.1,0.91,0.92,0.93,0.94,2.2,2.4\n6682.2,nan,nan,0.83,0.84,3.2,3.4\n6682.3,0.93,0.94,0.83,0.84,3.3,3.5\n"
    }
    """
    When The raw data is sent through a "<type>" process at "<url>"
    Then The JSON value of type should be "<type>"
    And The JSON value of id should be "<id>"
    And The JSON value of features should be "<features>"
    And The JSON value of data should be "<data>"
    Examples:
    |url                                                   | type          | id        | features                                          | data                                                                                                            |
    |http://142.93.109.50:5000/api/eyetracker/substitution | substitution  | Cucumber  | timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR | 6682.1000000000,0.9100000000,0.9200000000,0.9300000000,0.9400000000,2.2000000000,2.4000000000\n6682.2000000000,0.8300000000,0.8400000000,0.8300000000,0.8400000000,3.2000000000,3.4000000000\n6682.3000000000,0.9300000000,0.9400000000,0.8300000000,0.8400000000,3.3000000000,3.5000000000\n|
    |http://142.93.109.50:5000/api/eyetracker/avgPupil     | avgPupil      | Cucumber  | avgPupilL,avgPupilR                               | 2.9,3.1                                                                                                     |
    |http://142.93.109.50:5000/api/eyetracker/interpolate  | interpolate   | Cucumber  | timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR | 6682.1000000000,0.9100000000,0.9200000000,0.9300000000,0.9400000000,2.2000000000,2.4000000000\n6682.2000000000,0.9200000000,0.9300000000,0.8300000000,0.8400000000,3.2000000000,3.4000000000\n6682.3000000000,0.9300000000,0.9400000000,0.8300000000,0.8400000000,3.3000000000,3.5000000000\n|
