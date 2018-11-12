# from flask import Flask, redirect, url_for, request, jsonify

# documentation
# https://flask-restplus.readthedocs.io/en/stable/api.html

from flask import Flask, request, jsonify
from flask_restplus import Api, Resource, fields
from eyetracker import *
app = Flask(__name__)
api = Api(app, version='1.0', title='Feature Extraction and cleaning API')

api = api.namespace('api', description='API operations')

# expect
raw = api.model('raw',{
    'type': fields.String(required=True, description='type of data', example='raw'),
    'id': fields.String(required=True, description='id of the data', example='FlaskTest'),
    'features':fields.String(required=True, description='List of set of features', example='timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR'),
    'data': fields.String(required=True, description='The dataset that is in need for cleaning', example='1,2,3,4,5,6,7\n7,6,5,nan,3,2,nan')
})
# Response
preprocessed = api.model('preprocessed',{
    'type': fields.String(required=True, description='type of data', example='preprocessed'),
    'id': fields.String(required=True, description='id of the data', example='FlaskTest'),
    'features':fields.String(required=True, description='List of set of features', example='timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR'),
    'data': fields.String(required=True, description='The dataset that is in need for cleaning', example='1,2,3,4,5,6,7\n7,6,5,6,3,2,2')
})

# Response
avgPupil = api.model('avgPupil',{
    'type': fields.String(required=True, description='type of data', example='avgPupil'),
    'id': fields.String(required=True, description='id of the data', example='FlaskTest'),
    'features':fields.String(required=True, description='List of features', example='pupilL,pupilR'),
    'data': fields.String(required=True, description='Average pupil Diameter',example='1,2')
})




@api.route('/eyetracker/substitution')
class Eyetracker(Resource):
    @api.doc('substituion_cleaning')
    @api.expect(raw)
    @api.marshal_with(preprocessed, code=200)
    def post(self):
        return Clean.substitution(api.payload)


@api.route('/eyetracker/avgPupil')
class AvgPupilD(Resource):
    @api.doc('pupil_avg')
    @api.expect(preprocessed)
    @api.marshal_with(avgPupil, code=200)
    def post(self):
        return FeatureExtraction.averagePupilDiameter(api.payload)




if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
