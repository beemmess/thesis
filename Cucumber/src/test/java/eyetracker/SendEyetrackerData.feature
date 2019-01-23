# Created by beemmess at 02/11/2018
Feature: Send a raw Eyetracking data to Processing server


  Scenario: As a researcher I want the eyetracking data to be successfully sent to the server
    Given That the eyetracking data of the user is collected and sent as a JSON string to the server:
    """
    {
    "type" : "raw",
    "id": "Cucumber",
    "device":"eyetracker",
    "apiUrl":"/eyetracker/substitution,/eyetracker/avgPupil,/eyetracker/avgPupil/perTask,/eyetracker/interpolate",
    "attributes": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task",
    "data": "6682,90,91,93,94,22,24,t1\n6683,NaN,NaN,83,84,32,34,t1\n6684,92,94,83,84,32,38,t2\n6685,93,95,83,84,34,36,t2\n"
    }
    """
    When The raw data is sent to the server "http://hbl-wildfly.compute.dtu.dk:8080/ProcessingServer/api/data"
    Then The data is succesfully sent and the server code response should be <200>
    And The message respond replies that all data has been saved to database;

  Scenario: As a researcher, I want to send a badly formatted eyetracking data to the server so that the respond code is a valid error response
    Given That the eyetracking data of the user is collected and sent as a JSON string to the server:
    """
    {
    "id": "Cucumber"
    "device":"eyetracker",
    "apiUrl":"/eyetracker/substitution,/eyetracker/avgPupil,/eyetracker/avgPupil/perTask,/eyetracker/interpolate",
    "attributes": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task"
    "data": "6682,90,91,93,94,22,24,t1\n6683,NaN,NaN,83,84,32,34,t1\n6684,92,94,83,84,32,38,t2\n6685,93,95,83,84,34,36,t2\n"
    """
    When The raw data is sent to the server "http://hbl-wildfly.compute.dtu.dk:8080/ProcessingServer/api/data"
    Then The raw data is unsuccesfully sent to the server and respond code is <400>


  Scenario Outline: As a researcher, I want perform series of processing procedures on the eye tracking data so that I can use the processed data for further analysis
    Given That the eyetracking data of the user has been sent to the server as a JSON:
    """
    {
    "type" : "raw",
    "id": "Cucumber",
    "device":"eyetracker",
    "apiUrl":"/eyetracker/substitution,/eyetracker/avgPupil,/eyetracker/avgPupil/perTask,/eyetracker/interpolate",
    "attributes": "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task",
    "data": "6682,90,91,93,94,22,24,t1\n6683,NaN,NaN,83,84,32,34,t1\n6684,92,94,83,84,32,38,t2\n6685,93,95,83,84,34,36,t2\n"
    }
    """

    When The raw data is sent through a "<type>" process at "<url>"
    Then The JSON value of type should be "<type>"
    And The JSON value of id should be "<id>"
    And The JSON value of attributes should be "<attributes>"
    And The JSON value of data should be "<data>"
    Examples:
    |url                                                               | type          | id        | attributes                                             | data                                                                                                            |
    |http://hbl-wildfly.compute.dtu.dk:5000/eyetracker/substitution    | substitution  | Cucumber  | timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task | 6682,90.0,91.0,93,94,22,24,t1\n6683,83.0,84.0,83,84,32,34,t1\n6684,92.0,94.0,83,84,32,38,t2\n6685,93.0,95.0,83,84,34,36,t2\n|
    |http://hbl-wildfly.compute.dtu.dk:5000/eyetracker/avgPupil        | avgPupil      | Cucumber  | avgPupilL,avgPupilR                                    | 30.0,33.0                                                                                                     |
    |http://hbl-wildfly.compute.dtu.dk:5000/eyetracker/interpolate     | interpolate   | Cucumber  | timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task | 6682,90.0,91.0,93,94,22,24,t1\n6683,91.0,92.5,83,84,32,34,t1\n6684,92.0,94.0,83,84,32,38,t2\n6685,93.0,95.0,83,84,34,36,t2\n|
    |http://hbl-wildfly.compute.dtu.dk:5000/eyetracker/avgPupil/perTask| avgPupilTasks | Cucumber  | avgPupilL,avgPupilR,task                               | 27.0,29.0,t1\n33.0,37.0,t2\n|

