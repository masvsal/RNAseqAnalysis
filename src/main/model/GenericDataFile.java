package model;

import model.interfaces.NamedFile;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

//represents a nondescript data file having a name, description and data. Data is stored as a string.
public class GenericDataFile implements NamedFile, Writable {

    private String name;            //name of data file
    private String description;     //description of content of data file, not required
    private String data;            //data contained within data file, formatted as String

    //EFFECT: instantiates a generic data file with the given name and some given data in a string format
    public GenericDataFile(String name, String data) {
        this.name = name;
        this.data = data;
        this.description = "";
    }


    @Override
    public String getName() {
        return this.name;
    }

    //EFFECT: returns description of experiment as a string. If description is NULL, returns "no description"
    @Override
    public String getDescription() {
        if (Objects.equals(this.description, "")) {
            return "no description";
        } else {
            return this.description;
        }
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    //EFFECT: Returns the data contained within the Data File.
    public String getData() {
        return data;
    }

    //MODIFIES: this
    //EFFECT: Sets the data field of named file to the given string.
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("data", data);

        return jsonObject;
    }

}
