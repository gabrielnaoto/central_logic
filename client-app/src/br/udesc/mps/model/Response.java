/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.mps.model;

/**
 *
 * @author ignoi
 */
public class Response {

    private int status;
    private Object[] values;

    public Response() {
    }

    public Response(int status, Object[] values) {
        this.status = status;
        this.values = values;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Response{" + "status=" + status + ", values=" + values + '}';
    }

}
