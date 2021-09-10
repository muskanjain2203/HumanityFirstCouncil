package com.example.humanityfirstcouncil.model;

import java.util.ArrayList;

public class GoalsResponse extends CommonResponse{

    public class GoalList{
        private int goalId;
        private String goalTittle;
        private String goalDescription;
        private String url;

        public int getGoalId() {
            return goalId;
        }

        public String getGoalTittle() {
            return goalTittle;
        }

        public String getGoalDescription() {
            return goalDescription;
        }

        public String getUrl() {
            return url;
        }

        public void setGoalId(int goalId) {
            this.goalId = goalId;
        }

        public void setGoalTittle(String goalTittle) {
            this.goalTittle = goalTittle;
        }

        public void setGoalDescription(String goalDescription) {
            this.goalDescription = goalDescription;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    public ArrayList<GoalList> list = new ArrayList<>();

    public ArrayList getList() {
        return list;
    }
}
