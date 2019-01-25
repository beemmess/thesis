package model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * A class that is used for deserilising and serilising the JSON string that contains
 * all the data, and information about the data
 */
@ApiModel
public class DataMessage extends Message {

    private String id;
    private String attributes;
    private String data;
    private String type;
    private String apiUrl;
    private String device;

    @ApiModelProperty(example = "1,0.3,0.4,0.2,0.3,2.34,2.45,read\n2,0.3,0.5,0.1,0.4,2.64,2.55,listen\n", required = true, value = "Collected data from, e.g. eye tracker")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @ApiModelProperty(example = "timestamp,leftX,leftY,rightX,rightY,pupilL,pupilR,task", required = true, value = "List of attributes from, e.g. eye tracker")
    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    @ApiModelProperty(example = "swaggerId", required = true, value = "Data id for, e.g. eye tracker")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(example = "raw", required = true, value = "type of data for, e.g. eye tracker")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @ApiModelProperty(example = "eyetracker", required = true, value = "Device type, e.g. eye tracker")
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @ApiModelProperty(example = "/eyetracker/substitution,/eyetracker/avgPupil,/eyetracker/avgPupil/perTask,/eyetracker/interpolate", required=true, value = "List of processing procedure for, e.g. eye tracker")
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}