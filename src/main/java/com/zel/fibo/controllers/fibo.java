package com.zel.fibo.controllers;

import com.zel.fibo.models.Sucesion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@RestController
public class fibo {

    private Object List;
    private Object Sucesion;

    //Este es el endpoint que procesa el valor y regresa la serie de fibonacci.
    @GetMapping("fibo/{n}")
    public Sucesion getFibonacci(@PathVariable String n){
        String response = "";
        if(isNumeric(n)){
            int value = Integer.parseInt(n);
            if(value>=0){
                for (int i = 0; i < value; i++) {
                    response += (fibonacci(i)) + (" ");
                }
                saveRequest(n);
            } else {
                response = ("Error [número negativo].");
            }
        } else {
            response = ("Error [se ingreso un valor incorrecto, verifique sus parametros].");
        }

        return new Sucesion(response);
    }

    @DeleteMapping("fibo/{n}")
    public String removeRequest(@PathVariable String n){
        if(deleteRequest(n))
            return "Eliminado con exito";
        return "No se encontro ningun registro para ese numero";
    }

    //Esta funcion me regresa los valores de la sucesion.
    public int fibonacci(int n) {
        if (n>1){
            return fibonacci(n-1) + fibonacci(n-2);  //función recursiva
        }
        else if (n==1) {
            return 1;
        }
        else if (n==0){
            return 0;
        }
        else{
            return -1;
        }
    }

    //Esta funcion me ayuda a ver si el parametro incluido en la url de la peticion es un valor entero o no.
    public boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    //Guardo una peticion, claro que solo la llamo cuando es una peticion valida.
    public void saveRequest(String n) {
        JSONArray jsonArray = new JSONArray();
        jsonArray = readFile();
        JSONObject request = new JSONObject();
        request.put("n", n);
        request.put("date", LocalDate.now().toString());
        jsonArray.add(request);

        try {
            FileWriter fileWriter = new FileWriter("data.json");
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Leo todas las peticiones de mi archivo JSON.
    public JSONArray readFile() {
        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("data.json");
            Object object = jsonParser.parse(fileReader);
            JSONArray requests = (JSONArray) object;
            for( Object request : requests){
                jsonArray.add(request);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    //Me permite eliminar registros de numeros registrados en nuestro archivo JSON (elimina la primer coincidencia),
    //returna true si fue correcto/false si encontro coincidencias .
    public boolean deleteRequest(String n) {
        JSONArray jsonArray = new JSONArray();
        jsonArray = readFile();
        for(Object object : jsonArray) {
            JSONObject jsonObject1 = (JSONObject) object;
            if(jsonObject1.get("n").equals(n)){
                jsonArray.remove(jsonObject1);
                try {
                    FileWriter fileWriter = new FileWriter("data.json");
                    fileWriter.write(jsonArray.toJSONString());
                    fileWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return  false;
    }
}
