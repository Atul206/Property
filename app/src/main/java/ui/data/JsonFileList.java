package ui.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JsonFileList implements Serializable {

    @SerializedName("Sheet1")
    @Expose
    private List<JsonToPojo> Sheet1;

    public List<JsonToPojo> getSheet1() {
        return Sheet1;
    }

    public void setSheet1(List<JsonToPojo> sheet1) {
        Sheet1 = sheet1;
    }
}
