# Created by beemmess at 02/11/2018
Feature: Send a raw Shimmer data to Feature extraction server


  Scenario: As a user I want the Shimmer data to be successfully sent to the server
    Given That the shimmer data of the user is collected and sent as a JSON string to the server:
    """
    {
    "type" : "raw",
    "id": "Cucumber",
    "device":"shimmer",
    "apiUrl":"/shimmer/normalize",
    "attributes": "timestamp,GSR,PPG,task",
    "data": "6681,1,2,1\n6683,3,2,2\n6684,2,1,3\n"
    }
    """
    When The raw data is sent to the server "http://139.59.128.154:8080/ProcessingServer/api/data"
    Then The data is succesfully sent and the server code response should be <200>

  Scenario: As a user, I want to send a badly formatted Shimmer data to the server so that the respond code is a valid error response
    Given That the shimmer data of the user is collected and sent as a JSON string to the server:
    """
    {
    "id": "Cucumber"
    "device":"shimmer",
    "apiUrl":"/shimmer/normalize",
    "attributes": "timestamp,GSR,PPG,task",
    "data": "6681,1,2,1\n6683,3,2,2\n6684,2,1,3\n"
    }
    """
    When The raw data is sent to the server "http://139.59.128.154:8080/ProcessingServer/api/data"
    Then The raw data is unsuccesfully sent to the server and respond code is <400>


  Scenario Outline: As a user, I want process the data so that it is correctly processed
    Given That the shimmer data of the user has been sent to the server as a JSON:
    """
    {
    "type" : "raw",
    "id": "Cucumber",
    "device":"shimmer",
    "apiUrl":"/shimmer/normalize",
    "attributes": "timestamp,GSR,PPG,task",
    "data": "6681,1,2,1\n6683,3,2,2\n6684,2,1,3\n"
    }
    """
    When The raw data is sent through a "<type>" process at "<url>"
    Then The JSON value of type should be "<type>"
    And The JSON value of id should be "<id>"
    And The JSON value of attributes should be "<attributes>"
    And The JSON value of data should be "<data>"
    Examples:
    |url                                                | type          | id        | attributes            | data                                                                                                                                   |
    |http://139.59.128.154:5000/shimmer/normalize    | normalize     | Cucumber  | timestamp,GSR,PPG,task | 6681,0.5,1.2,1\n6683,1.5,1.2,2\n6684,1.0,0.6,3\n|
