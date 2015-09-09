package com.blackwhite.bean;

import java.util.List;

/**
 * Created by BlackWhite on 15/9/9.
 * JsonBean,返回的Json数据生成的实体
 */
public class JsonBean {

    /**
     * subjects : [{"score":"78","subject":"编译原理","grade":"3"},{"score":"76","subject":"软件体系结构","grade":"2"},{"score":"82","subject":"计算机网络与互联网","grade":"3"},{"score":"91","subject":"数据库系统原理","grade":"3"},{"score":"优秀","subject":"编译原理课程设计","grade":"1"},{"score":"优秀","subject":"数据库系统原理课程设计","grade":"1"},{"score":"95","subject":"计算机网络与互联网课程设计","grade":"1"}]
     * credit : 4.25
     */
    private List<SubjectsEntity> subjects;
    private double credit;

    public void setSubjects(List<SubjectsEntity> subjects) {
        this.subjects = subjects;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public List<SubjectsEntity> getSubjects() {
        return subjects;
    }

    public double getCredit() {
        return credit;
    }

    public static class SubjectsEntity {
        /**
         * score : 79
         * subject : 编译原理
         * grade : 3
         */
        private String score;
        private String subject;
        private String grade;

        public void setScore(String score) {
            this.score = score;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getScore() {
            return score;
        }

        public String getSubject() {
            return subject;
        }

        public String getGrade() {
            return grade;
        }
    }
}
