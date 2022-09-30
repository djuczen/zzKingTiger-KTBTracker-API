package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


public class Requirements {

    //
    // PHYSICAL REQUIREMENTS
    //

    /**
     * Count of miles run/walked by the candidate.
     */
    private double miles;

    /**
     * Count of push-ups performed by the candidate.
     */
    private int pushUps;

    /**
     * Count of sit-ups performed by the candidate.
     */
    private int sitUps;

    /**
     * Count of burpees performed by the candidate.
     */
    private int burpees;

    /**
     * Count of kicks (front, side, back) performed by the candidate.
     */
    private int kicks;

    /**
     * Count of poomsaes performed by the candidate.
     */
    private int poomsae;

    /**
     * Count of self-defense performed by the candidate.
     */
    private int selfDefense;

    /**
     * Count of 90-second sparring rounds performed by the candidate.
     */
    private double sparring;

    /**
     * Count of 1-minute jump-rope sessions performed by the candidate.
     */
    private double jumps;

    /**
     * Count of pull-ups performed by the candidate.
     */
    private int pullUps;

    /**
     * Count of 1-minute planks performed by the candidate.
     */
    private int planks;

    /**
     * Count of rolls and falls performed by the candidate.
     */
    private int rollsFalls;

    //
    // CLASS REQUIREMENTS
    //

    /**
     * Count of Saturday Black Belt classes attended by the candidate.
     *
     * @since 1.0.0
     */
    private int classSaturday;

    /**
     * Count of weekday classes attended by the candidate.
     */
    private int classWeekday;

    /**
     * Count of Philippine Martial Arts Association (PMAA) classes attended by the candidate.
     */
    private int classPMAA;

    /**
     * Count of Olympic Sparring classes attended by the candidate
     */
    private int classSparring;

    /**
     * Count of MasterQuest classes attended by the candidate
     */
    private int classMasterQ;

    /**
     * Count of DreamTeam classes attended by the candidate
     */
    private int classDreamTeam;

    /**
     * Count of HyperPro classes attended by the candidate
     *
     * @since 1.0.0
     */
    private int classHyperPro;

    //
    // OTHER REQUIREMENTS
    //

    /**
     * Count of minutes of meditation spent by the candidate
     *
     * @since 2.0.0
     */
    private double meditation;

    /**
     * Count of Random Acts of Kindness (RAOK) performed by the candidate @since 1.0.0
     */
    private int randomActs;

    /**
     * Count of 30-minute sessions when the candidate mentors someone
     *
     * @since 1.0.0
     */
    private int mentor;

    /**
     * Count of 30-minute sessions when the candidate is mentored by someone
     *
     * @since 1.0.0
     */
    private int mentee;

    private int leadership;

    private int leadership2;


    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public int getPushUps() {
        return pushUps;
    }

    public void setPushUps(int pushUps) {
        this.pushUps = pushUps;
    }

    public int getSitUps() {
        return sitUps;
    }

    public void setSitUps(int sitUps) {
        this.sitUps = sitUps;
    }

    public int getBurpees() {
        return burpees;
    }

    public void setBurpees(int burpees) {
        this.burpees = burpees;
    }

    public int getKicks() {
        return kicks;
    }

    public void setKicks(int kicks) {
        this.kicks = kicks;
    }

    public int getPoomsae() {
        return poomsae;
    }

    public void setPoomsae(int poomsae) {
        this.poomsae = poomsae;
    }

    public int getSelfDefense() {
        return selfDefense;
    }

    public void setSelfDefense(int selfDefense) {
        this.selfDefense = selfDefense;
    }

    public double getSparring() {
        return sparring;
    }

    public void setSparring(double sparring) {
        this.sparring = sparring;
    }

    public double getJumps() {
        return jumps;
    }

    public void setJumps(double jumps) {
        this.jumps = jumps;
    }

    public int getPullUps() {
        return pullUps;
    }

    public void setPullUps(int pullUps) {
        this.pullUps = pullUps;
    }

    public int getPlanks() {
        return planks;
    }

    public void setPlanks(int planks) {
        this.planks = planks;
    }

    public int getRollsFalls() {
        return rollsFalls;
    }

    public void setRollsFalls(int rollsFalls) {
        this.rollsFalls = rollsFalls;
    }

    public int getClassSaturday() {
        return classSaturday;
    }

    public void setClassSaturday(int classSaturday) {
        this.classSaturday = classSaturday;
    }

    public int getClassWeekday() {
        return classWeekday;
    }

    public void setClassWeekday(int classWeekday) {
        this.classWeekday = classWeekday;
    }

    public int getClassPMAA() {
        return classPMAA;
    }

    public void setClassPMAA(int classPMAA) {
        this.classPMAA = classPMAA;
    }

    public int getClassSparring() {
        return classSparring;
    }

    public void setClassSparring(int classSparring) {
        this.classSparring = classSparring;
    }

    public int getClassMasterQ() {
        return classMasterQ;
    }

    public void setClassMasterQ(int classMasterQ) {
        this.classMasterQ = classMasterQ;
    }

    public int getClassDreamTeam() {
        return classDreamTeam;
    }

    public void setClassDreamTeam(int classDreamTeam) {
        this.classDreamTeam = classDreamTeam;
    }

    public int getClassHyperPro() {
        return classHyperPro;
    }

    public void setClassHyperPro(int classHyperPro) {
        this.classHyperPro = classHyperPro;
    }

    public double getMeditation() {
        return meditation;
    }

    public void setMeditation(double meditation) {
        this.meditation = meditation;
    }

    public int getRandomActs() {
        return randomActs;
    }

    public void setRandomActs(int randomActs) {
        this.randomActs = randomActs;
    }

    public int getMentor() {
        return mentor;
    }

    public void setMentor(int mentor) {
        this.mentor = mentor;
    }

    public int getMentee() {
        return mentee;
    }

    public void setMentee(int mentee) {
        this.mentee = mentee;
    }

    public int getLeadership() {
        return leadership;
    }

    public void setLeadership(int leadership) {
        this.leadership = leadership;
    }

    public int getLeadership2() {
        return leadership2;
    }

    public void setLeadership2(int leadership2) {
        this.leadership2 = leadership2;
    }
}
