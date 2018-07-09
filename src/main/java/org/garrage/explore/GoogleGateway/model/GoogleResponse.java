package org.garrage.explore.GoogleGateway.model;

import com.fasterxml.jackson.annotation.*;
import org.garrage.explore.GoogleGateway.model.Candidate;
import org.garrage.explore.GoogleGateway.model.DebugLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "candidates",
        "debug_log",
        "status"
})
public class GoogleResponse {

    @JsonProperty("candidates")
    private List<Candidate> candidates = null;
    @JsonProperty("debug_log")
    private DebugLog debugLog;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("candidates")
    public List<Candidate> getCandidates() {
        return candidates;
    }

    @JsonProperty("candidates")
    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    @JsonProperty("debug_log")
    public DebugLog getDebugLog() {
        return debugLog;
    }

    @JsonProperty("debug_log")
    public void setDebugLog(DebugLog debugLog) {
        this.debugLog = debugLog;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

