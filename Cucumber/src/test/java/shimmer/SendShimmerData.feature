# Created by beemmess at 02/11/2018
Feature: Send a raw Shimmer data to Feature extraction server


  Scenario: As a user I want the Shimmer data to be successfully sent to the server
    Given That the shimmer data of the user is collected and sent as a JSON string to the server:
    """
    {
    "type" : "raw",
    "id": "Cucumber",
    "features": "timestamp,GSR,PPG,task",
    "data": "6682.1,1.1,1.2,1\n6682.2,2.1,2.2,2\n6682.3,3.1,3.2,3\n"
    }
    """
    When The raw data is sent to the server "http://142.93.109.50:8080/FeatureExtractionServer/api/shimmer"
    Then The data is succesfully sent and the server code response should be <200>

  Scenario: As a user, I mistakenly send a badly formatted Shimmer data to the server
    Given That the shimmer data of the user is collected and sent as a JSON string to the server:
    """
    {
    "id": "Cucumber"
    "features": "timestamp,GSR,PPG,task",
    "data": "6682.1,1.1,1.2,1\n6682.2,2.1,2.2,2\n6682.3,3.1,3.2,3\n"
    }
    """
    When The raw data is sent to the server "http://142.93.109.50:8080/FeatureExtractionServer/api/shimmer"
    Then The raw data is unsuccesfully sent to the server and respond code is <400>


  Scenario Outline: As a researcher, I want to be sure that the data is processed correctly
    Given That the shimmer data of the user has been sent to the server as a JSON:
    """
    {
    "type" : "raw",
    "id": "Cucumber",
    "features": "timestamp,GSR,PPG,task",
    "data": "6682.1,1.11,1.12,1\n6682.2,1.13,1.14,2\n6682.3,1.15,1.16,3\n"
    }
    """
    When The raw data is sent through a "<type>" process at "<url>"
    Then The JSON value of type should be "<type>"
    And The JSON value of id should be "<id>"
    And The JSON value of features should be "<features>"
    And The JSON value of data should be "<data>"
    Examples:
    |url                                                | type          | id        | features               | data                                                                                                                                   |
    |http://142.93.109.50:5000/shimmer/normalize    | normalize     | Cucumber  | timestamp,GSR,PPG,task | 6682.1000000000,0.9823008850,0.9824561404,1\n6682.2000000000,1.0000000000,1.0000000000,2\n6682.3000000000,1.0176991150,1.0175438596,3\n|
