package com.example.ash.appackers;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// this class extending StringRequest to allow us to make a request to the Register.php file on the server and get responds on the string
public class RegisterRequest extends StringRequest {

    // specify the url where our Register.php is at
    // static and final cause it is not gonna change through out our program
    private static final String REGISTER_REQUEST_URL = "http://appackers.000webhostapp.com/Register.php";
    // Create a map to
    private Map<String, String> params;

    public RegisterRequest(String email, String username, String password, String full_name, String nationality, int age,
                           String about, String gender, Response.Listener<String> listener){

        // super to pass some data to volley db which will allow us to execute a request for us
        // first parameter is the method - method.post to send data to Register.php and Register.php will respond some data
        // listener is when a volley has finished with the request, it's going to inform this listener
        // we also need to give an error listener - for now, just leave it as null
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        // using params to make volley also parse this information to the request
        params = new HashMap<>();
        // put the data into the HashMap
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
        params.put("full_name", full_name);
        params.put("nationality", nationality);
        params.put("age", age + ""); // add string to convert from int to string
        params.put("about", about);
        params.put("gender", gender);

    }

    // volley need to access to this data
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
