package com.affiancesolutions.kingtiger.ktbtracker.client.model;

import java.io.Serializable;
import java.time.LocalDate;

public class StatisticsResponse implements Serializable {

    private static final double serialVersionUID = -79473790983979594L;

    private int candidateId;

    private LocalDate startDate;

    private LocalDate endDate;

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
    private double pushUps;

    /**
     * Count of sit-ups performed by the candidate.
     */
    private double sitUps;

    /**
     * Count of burpees performed by the candidate.
     */
    private double burpees;

    /**
     * Count of kicks (front, side, back) performed by the candidate.
     */
    private double kicks;

    /**
     * Count of poomsaes performed by the candidate.
     */
    private double poomsae;

    /**
     * Count of self-defense performed by the candidate.
     */
    private double selfDefense;

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
    private double pullUps;

    /**
     * Count of 1-minute planks performed by the candidate.
     */
    private double planks;

    /**
     * Count of rolls and falls performed by the candidate.
     */
    private double rollsFalls;

    //
    // CLASS REQUIREMENTS
    //

    /**
     * Count of Saturday Black Belt classes attended by the candidate.
     *
     * @since 1.0.0
     */
    private double classSaturday;

    /**
     * Count of weekday classes attended by the candidate.
     */
    private double classWeekday;

    /**
     * Count of Philippine Martial Arts Association (PMAA) classes attended by the candidate.
     */
    private double classPMAA;

    /**
     * Count of Olympic Sparring classes attended by the candidate
     */
    private double classSparring;

    /**
     * Count of MasterQuest classes attended by the candidate
     */
    private double classMasterQ;

    /**
     * Count of DreamTeam classes attended by the candidate
     */
    private double classDreamTeam;

    /**
     * Count of HyperPro classes attended by the candidate
     *
     * @since 1.0.0
     */
    private double classHyperPro;

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
    private double randomActs;

    /**
     * Count of 30-minute sessions when the candidate mentors someone
     *
     * @since 1.0.0
     */
    private double mentor;

    /**
     * Count of 30-minute sessions when the candidate is mentored by someone
     *
     * @since 1.0.0
     */
    private double mentee;

    private double leadership;

    private double leadership2;

    private long journals;


    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getMiles() {
        return miles;
    }

    public void setMiles(double miles) {
        this.miles = miles;
    }

    public double getPushUps() {
        return pushUps;
    }

    public void setPushUps(double pushUps) {
        this.pushUps = pushUps;
    }

    public double getSitUps() {
        return sitUps;
    }

    public void setSitUps(double sitUps) {
        this.sitUps = sitUps;
    }

    public double getBurpees() {
        return burpees;
    }

    public void setBurpees(double burpees) {
        this.burpees = burpees;
    }

    public double getKicks() {
        return kicks;
    }

    public void setKicks(double kicks) {
        this.kicks = kicks;
    }

    public double getPoomsae() {
        return poomsae;
    }

    public void setPoomsae(double poomsae) {
        this.poomsae = poomsae;
    }

    public double getSelfDefense() {
        return selfDefense;
    }

    public void setSelfDefense(double selfDefense) {
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

    public double getPullUps() {
        return pullUps;
    }

    public void setPullUps(double pullUps) {
        this.pullUps = pullUps;
    }

    public double getPlanks() {
        return planks;
    }

    public void setPlanks(double planks) {
        this.planks = planks;
    }

    public double getRollsFalls() {
        return rollsFalls;
    }

    public void setRollsFalls(double rollsFalls) {
        this.rollsFalls = rollsFalls;
    }

    public double getClassSaturday() {
        return classSaturday;
    }

    public void setClassSaturday(double classSaturday) {
        this.classSaturday = classSaturday;
    }

    public double getClassWeekday() {
        return classWeekday;
    }

    public void setClassWeekday(double classWeekday) {
        this.classWeekday = classWeekday;
    }

    public double getClassPMAA() {
        return classPMAA;
    }

    public void setClassPMAA(double classPMAA) {
        this.classPMAA = classPMAA;
    }

    public double getClassSparring() {
        return classSparring;
    }

    public void setClassSparring(double classSparring) {
        this.classSparring = classSparring;
    }

    public double getClassMasterQ() {
        return classMasterQ;
    }

    public void setClassMasterQ(double classMasterQ) {
        this.classMasterQ = classMasterQ;
    }

    public double getClassDreamTeam() {
        return classDreamTeam;
    }

    public void setClassDreamTeam(double classDreamTeam) {
        this.classDreamTeam = classDreamTeam;
    }

    public double getClassHyperPro() {
        return classHyperPro;
    }

    public void setClassHyperPro(double classHyperPro) {
        this.classHyperPro = classHyperPro;
    }

    public double getMeditation() {
        return meditation;
    }

    public void setMeditation(double meditation) {
        this.meditation = meditation;
    }

    public double getRandomActs() {
        return randomActs;
    }

    public void setRandomActs(double randomActs) {
        this.randomActs = randomActs;
    }

    public double getMentor() {
        return mentor;
    }

    public void setMentor(double mentor) {
        this.mentor = mentor;
    }

    public double getMentee() {
        return mentee;
    }

    public void setMentee(double mentee) {
        this.mentee = mentee;
    }

    public double getLeadership() {
        return leadership;
    }

    public void setLeadership(double leadership) {
        this.leadership = leadership;
    }

    public double getLeadership2() {
        return leadership2;
    }

    public void setLeadership2(double leadership2) {
        this.leadership2 = leadership2;
    }

    public long getJournals() {
        return journals;
    }

    public void setJournals(long journals) {
        this.journals = journals;
    }
}
