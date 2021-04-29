package com.todolist.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoList {
    @Singular
    private final ArrayList<Item> items = new ArrayList<>();
    public String email;
    public ArrayList<Item> itemList;

    public TodoList() {
        this.itemList = new ArrayList<>();
    }

    public void addItem(Item itemToAdd){
        if(this.isListFull()){
            return;
        }
        this.itemList.add(itemToAdd);
        if(this.itemList.size() == 8 ){
            // sendEmail()
        }
    }

    public boolean isListFull(){
        if ( this.itemList.size() < 10){
            return false;
        }
        return true;
    }
}
