# Created by beemmess at 02/11/2018
Feature: Send a raw Eyetracking data to Feature extraction server

  Scenario: As a user I want my eyetracking data to be successfully sent to the server
    Given That the eyetracking data of the user is collected into "eyetracker_data.json"
    When The raw data is sent to the server "http://142.93.109.50:9090/FeatureExtractionServer/api/eyetracker"
    Then The raw data is succesfully sent to the server and respond code is <200>

