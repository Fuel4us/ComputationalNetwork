/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Diogo Monteiro - (1140302)
 */
public class AppModel {
    private List<Wall> wallList;
    private Wall wallInUse;

    public AppModel() {
        this.wallList=new ArrayList<>();
    }

    public Wall getWallInUse() {
        return wallInUse;
    }

    public void setWallInUse(Wall wallInUse) {
        this.wallInUse = wallInUse;
    }

    
    public List<Wall> getWallList() {
        return wallList;
    }

    public void setWallList(List<Wall> wallList) {
        this.wallList = wallList;
    }
    
    public void addWall(Wall wall){
        if(!this.wallList.contains(wall)){
            this.wallList.add(wall);
        }
    }
    
    public void removeWall(Wall wall){
       if(this.wallList.contains(wall)){
            this.wallList.remove(wall);
        }
    }
    
    public void clear(){
        this.wallList.clear();
    }
    
    public Wall getWallByName(String wallName){
        for (Wall wall : wallList) {
            if(wall.getWallName().equalsIgnoreCase(wallName)){
                return wall;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.wallList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AppModel other = (AppModel) obj;
        if (!Objects.equals(this.wallList, other.wallList)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WallList{" + "wallList=" + wallList + '}';
    }
    
    
    
    
}
