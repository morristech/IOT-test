package com.cooey.api.client.models;

import com.cooey.android.users.old.utils.CTConstants;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import org.joda.time.DateTime;

public class DevicePowerAlertMessage {
    @SerializedName("altitude")
    private String altitude = null;
    @SerializedName("altitudeUncertainity")
    private String altitudeUncertainity = null;
    @SerializedName("assistedGPS")
    private String assistedGPS = null;
    @SerializedName("batteryStatus")
    private String batteryStatus = null;
    @SerializedName("command")
    private CommandEnum command = null;
    @SerializedName("commandExtension")
    private CommandExtensionEnum commandExtension = null;
    @SerializedName("commandId")
    private CommandIdEnum commandId = null;
    @SerializedName("createdOn")
    private DateTime createdOn = null;
    @SerializedName("deviceId")
    private String deviceId = null;
    @SerializedName("fullMessage")
    private String fullMessage = null;
    @SerializedName("gpsSpeed")
    private String gpsSpeed = null;
    @SerializedName("heading")
    private String heading = null;
    @SerializedName("horizontalUncertainity")
    private String horizontalUncertainity = null;
    @SerializedName("horizontalUncertainityAngle")
    private String horizontalUncertainityAngle = null;
    @SerializedName("id")
    private String id = null;
    @SerializedName("latitude")
    private Double latitude = null;
    @SerializedName("longitude")
    private Double longitude = null;
    @SerializedName("payloadSize")
    private Integer payloadSize = null;
    @SerializedName("perpendicularUncertainity")
    private String perpendicularUncertainity = null;
    @SerializedName("temperature")
    private String temperature = null;
    @SerializedName("timestamp")
    private Long timestamp = null;

    @JsonAdapter(Adapter.class)
    public enum CommandEnum {
        AUTHORIZE("AUTHORIZE"),
        UNIDENTIFIED("UNIDENTIFIED"),
        TRACKING_ALERT("TRACKING_ALERT"),
        GENERAL_REPORT("GENERAL_REPORT"),
        GEOFENCING_ALERT("GEOFENCING_ALERT"),
        ALERT(CTConstants.ALERT);
        
        private String value;

        private CommandEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }

        public static CommandEnum fromValue(String text) {
            for (CommandEnum b : values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonAdapter(Adapter.class)
    public enum CommandExtensionEnum {
        NONE("NONE"),
        EMERGENCY_ALERT_RPT("EMERGENCY_ALERT_RPT"),
        TEMPERATURE_ALERT_RPT("TEMPERATURE_ALERT_RPT"),
        SHOCK_ALERT_RPT("SHOCK_ALERT_RPT"),
        LOW_MOVMENT_ALERT_PRT("LOW_MOVMENT_ALERT_PRT"),
        DEVICE_POWER_ON_REPORT("DEVICE_POWER_ON_REPORT"),
        DEVICE_POWER_OFF_REPORT("DEVICE_POWER_OFF_REPORT"),
        HAZARDOUS_MOVEMENT_ALERT_RPT("HAZARDOUS_MOVEMENT_ALERT_RPT"),
        LOW_BATTERY_ALERT_RPT("LOW_BATTERY_ALERT_RPT"),
        FULL_BATTERY_ALERT_RPT("FULL_BATTERY_ALERT_RPT"),
        TRACKING_ALERT_RPT("TRACKING_ALERT_RPT"),
        GEOFENCING_ALERT("GEOFENCING_ALERT");
        
        private String value;

        private CommandExtensionEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }

        public static CommandExtensionEnum fromValue(String text) {
            for (CommandExtensionEnum b : values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @JsonAdapter(Adapter.class)
    public enum CommandIdEnum {
        AUTHORIZE("AUTHORIZE"),
        UNIDENTIFIED("UNIDENTIFIED"),
        TRACKING_ALERT("TRACKING_ALERT"),
        GENERAL_REPORT("GENERAL_REPORT"),
        GEOFENCING_ALERT("GEOFENCING_ALERT"),
        ALERT(CTConstants.ALERT);
        
        private String value;

        private CommandIdEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public String toString() {
            return String.valueOf(this.value);
        }

        public static CommandIdEnum fromValue(String text) {
            for (CommandIdEnum b : values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    public DevicePowerAlertMessage id(String id) {
        this.id = id;
        return this;
    }

    @ApiModelProperty("")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DevicePowerAlertMessage payloadSize(Integer payloadSize) {
        this.payloadSize = payloadSize;
        return this;
    }

    @ApiModelProperty("")
    public Integer getPayloadSize() {
        return this.payloadSize;
    }

    public void setPayloadSize(Integer payloadSize) {
        this.payloadSize = payloadSize;
    }

    public DevicePowerAlertMessage commandId(CommandIdEnum commandId) {
        this.commandId = commandId;
        return this;
    }

    @ApiModelProperty("")
    public CommandIdEnum getCommandId() {
        return this.commandId;
    }

    public void setCommandId(CommandIdEnum commandId) {
        this.commandId = commandId;
    }

    public DevicePowerAlertMessage commandExtension(CommandExtensionEnum commandExtension) {
        this.commandExtension = commandExtension;
        return this;
    }

    @ApiModelProperty("")
    public CommandExtensionEnum getCommandExtension() {
        return this.commandExtension;
    }

    public void setCommandExtension(CommandExtensionEnum commandExtension) {
        this.commandExtension = commandExtension;
    }

    public DevicePowerAlertMessage fullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
        return this;
    }

    @ApiModelProperty("")
    public String getFullMessage() {
        return this.fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }

    public DevicePowerAlertMessage deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    @ApiModelProperty("")
    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DevicePowerAlertMessage createdOn(DateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    @ApiModelProperty("")
    public DateTime getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public DevicePowerAlertMessage timestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @ApiModelProperty("")
    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public DevicePowerAlertMessage latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    @ApiModelProperty("")
    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public DevicePowerAlertMessage longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    @ApiModelProperty("")
    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public DevicePowerAlertMessage heading(String heading) {
        this.heading = heading;
        return this;
    }

    @ApiModelProperty("")
    public String getHeading() {
        return this.heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public DevicePowerAlertMessage altitude(String altitude) {
        this.altitude = altitude;
        return this;
    }

    @ApiModelProperty("")
    public String getAltitude() {
        return this.altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public DevicePowerAlertMessage assistedGPS(String assistedGPS) {
        this.assistedGPS = assistedGPS;
        return this;
    }

    @ApiModelProperty("")
    public String getAssistedGPS() {
        return this.assistedGPS;
    }

    public void setAssistedGPS(String assistedGPS) {
        this.assistedGPS = assistedGPS;
    }

    public DevicePowerAlertMessage gpsSpeed(String gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
        return this;
    }

    @ApiModelProperty("")
    public String getGpsSpeed() {
        return this.gpsSpeed;
    }

    public void setGpsSpeed(String gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public DevicePowerAlertMessage horizontalUncertainity(String horizontalUncertainity) {
        this.horizontalUncertainity = horizontalUncertainity;
        return this;
    }

    @ApiModelProperty("")
    public String getHorizontalUncertainity() {
        return this.horizontalUncertainity;
    }

    public void setHorizontalUncertainity(String horizontalUncertainity) {
        this.horizontalUncertainity = horizontalUncertainity;
    }

    public DevicePowerAlertMessage perpendicularUncertainity(String perpendicularUncertainity) {
        this.perpendicularUncertainity = perpendicularUncertainity;
        return this;
    }

    @ApiModelProperty("")
    public String getPerpendicularUncertainity() {
        return this.perpendicularUncertainity;
    }

    public void setPerpendicularUncertainity(String perpendicularUncertainity) {
        this.perpendicularUncertainity = perpendicularUncertainity;
    }

    public DevicePowerAlertMessage horizontalUncertainityAngle(String horizontalUncertainityAngle) {
        this.horizontalUncertainityAngle = horizontalUncertainityAngle;
        return this;
    }

    @ApiModelProperty("")
    public String getHorizontalUncertainityAngle() {
        return this.horizontalUncertainityAngle;
    }

    public void setHorizontalUncertainityAngle(String horizontalUncertainityAngle) {
        this.horizontalUncertainityAngle = horizontalUncertainityAngle;
    }

    public DevicePowerAlertMessage altitudeUncertainity(String altitudeUncertainity) {
        this.altitudeUncertainity = altitudeUncertainity;
        return this;
    }

    @ApiModelProperty("")
    public String getAltitudeUncertainity() {
        return this.altitudeUncertainity;
    }

    public void setAltitudeUncertainity(String altitudeUncertainity) {
        this.altitudeUncertainity = altitudeUncertainity;
    }

    public DevicePowerAlertMessage batteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
        return this;
    }

    @ApiModelProperty("")
    public String getBatteryStatus() {
        return this.batteryStatus;
    }

    public void setBatteryStatus(String batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public DevicePowerAlertMessage temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    @ApiModelProperty("")
    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public DevicePowerAlertMessage command(CommandEnum command) {
        this.command = command;
        return this;
    }

    @ApiModelProperty("")
    public CommandEnum getCommand() {
        return this.command;
    }

    public void setCommand(CommandEnum command) {
        this.command = command;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DevicePowerAlertMessage devicePowerAlertMessage = (DevicePowerAlertMessage) o;
        if (Objects.equals(this.id, devicePowerAlertMessage.id) && Objects.equals(this.payloadSize, devicePowerAlertMessage.payloadSize) && Objects.equals(this.commandId, devicePowerAlertMessage.commandId) && Objects.equals(this.commandExtension, devicePowerAlertMessage.commandExtension) && Objects.equals(this.fullMessage, devicePowerAlertMessage.fullMessage) && Objects.equals(this.deviceId, devicePowerAlertMessage.deviceId) && Objects.equals(this.createdOn, devicePowerAlertMessage.createdOn) && Objects.equals(this.timestamp, devicePowerAlertMessage.timestamp) && Objects.equals(this.latitude, devicePowerAlertMessage.latitude) && Objects.equals(this.longitude, devicePowerAlertMessage.longitude) && Objects.equals(this.heading, devicePowerAlertMessage.heading) && Objects.equals(this.altitude, devicePowerAlertMessage.altitude) && Objects.equals(this.assistedGPS, devicePowerAlertMessage.assistedGPS) && Objects.equals(this.gpsSpeed, devicePowerAlertMessage.gpsSpeed) && Objects.equals(this.horizontalUncertainity, devicePowerAlertMessage.horizontalUncertainity) && Objects.equals(this.perpendicularUncertainity, devicePowerAlertMessage.perpendicularUncertainity) && Objects.equals(this.horizontalUncertainityAngle, devicePowerAlertMessage.horizontalUncertainityAngle) && Objects.equals(this.altitudeUncertainity, devicePowerAlertMessage.altitudeUncertainity) && Objects.equals(this.batteryStatus, devicePowerAlertMessage.batteryStatus) && Objects.equals(this.temperature, devicePowerAlertMessage.temperature) && Objects.equals(this.command, devicePowerAlertMessage.command)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.payloadSize, this.commandId, this.commandExtension, this.fullMessage, this.deviceId, this.createdOn, this.timestamp, this.latitude, this.longitude, this.heading, this.altitude, this.assistedGPS, this.gpsSpeed, this.horizontalUncertainity, this.perpendicularUncertainity, this.horizontalUncertainityAngle, this.altitudeUncertainity, this.batteryStatus, this.temperature, this.command});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DevicePowerAlertMessage {\n");
        sb.append("    id: ").append(toIndentedString(this.id)).append("\n");
        sb.append("    payloadSize: ").append(toIndentedString(this.payloadSize)).append("\n");
        sb.append("    commandId: ").append(toIndentedString(this.commandId)).append("\n");
        sb.append("    commandExtension: ").append(toIndentedString(this.commandExtension)).append("\n");
        sb.append("    fullMessage: ").append(toIndentedString(this.fullMessage)).append("\n");
        sb.append("    deviceId: ").append(toIndentedString(this.deviceId)).append("\n");
        sb.append("    createdOn: ").append(toIndentedString(this.createdOn)).append("\n");
        sb.append("    timestamp: ").append(toIndentedString(this.timestamp)).append("\n");
        sb.append("    latitude: ").append(toIndentedString(this.latitude)).append("\n");
        sb.append("    longitude: ").append(toIndentedString(this.longitude)).append("\n");
        sb.append("    heading: ").append(toIndentedString(this.heading)).append("\n");
        sb.append("    altitude: ").append(toIndentedString(this.altitude)).append("\n");
        sb.append("    assistedGPS: ").append(toIndentedString(this.assistedGPS)).append("\n");
        sb.append("    gpsSpeed: ").append(toIndentedString(this.gpsSpeed)).append("\n");
        sb.append("    horizontalUncertainity: ").append(toIndentedString(this.horizontalUncertainity)).append("\n");
        sb.append("    perpendicularUncertainity: ").append(toIndentedString(this.perpendicularUncertainity)).append("\n");
        sb.append("    horizontalUncertainityAngle: ").append(toIndentedString(this.horizontalUncertainityAngle)).append("\n");
        sb.append("    altitudeUncertainity: ").append(toIndentedString(this.altitudeUncertainity)).append("\n");
        sb.append("    batteryStatus: ").append(toIndentedString(this.batteryStatus)).append("\n");
        sb.append("    temperature: ").append(toIndentedString(this.temperature)).append("\n");
        sb.append("    command: ").append(toIndentedString(this.command)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}