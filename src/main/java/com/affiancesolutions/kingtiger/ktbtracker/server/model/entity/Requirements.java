package com.affiancesolutions.kingtiger.ktbtracker.server.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Requirements {

    //
    // PHYSICAL REQUIREMENTS
    //

    /**
     * Count of miles run/walked by the candidate.
     */
    @Column(name = "MILES", nullable = false)
    private double miles;

    /**
     * Count of push-ups performed by the candidate.
     */
    @Column(name = "PUSHUPS", nullable = false)
    private int pushUps;

    /**
     * Count of sit-ups performed by the candidate.
     */
    @Column(name = "SITUPS", nullable = false)
    private int sitUps;

    /**
     * Count of burpees performed by the candidate.
     */
    @Column(name = "BURPEES", nullable = false)
    private int burpees;

    /**
     * Count of kicks (front, side, back) performed by the candidate.
     */
    @Column(name = "KICKS", nullable = false)
    private int kicks;

    /**
     * Count of poomsaes performed by the candidate.
     */
    @Column(name = "POOMSAE", nullable = false)
    private int poomsae;

    /**
     * Count of self-defense performed by the candidate.
     */
    @Column(name = "SELF_DEFENSE", nullable = false)
    private int selfDefense;

    /**
     * Count of 90-second sparring rounds performed by the candidate.
     */
    @Column(name = "SPARRING", nullable = false)
    private double sparring;

    /**
     * Count of 1-minute jump-rope sessions performed by the candidate.
     */
    @Column(name = "JUMPS", nullable = false)
    private double jumps;

    /**
     * Count of pull-ups performed by the candidate.
     */
    @Column(name = "PULLUPS", nullable = false)
    private int pullUps;

    /**
     * Count of 1-minute planks performed by the candidate.
     */
    @Column(name = "PLANKS", nullable = false)
    private int planks;

    /**
     * Count of rolls and falls performed by the candidate.
     */
    @Column(name = "ROLLS_FALLS", nullable = false)
    private int rollsFalls;

    //
    // CLASS REQUIREMENTS
    //

    /**
     * Count of Saturday Black Belt classes attended by the candidate.
     *
     * @since 1.0.0
     */
    @Column(name = "CLASS_SATURDAY", nullable = false)
    private int classSaturday;

    /**
     * Count of weekday classes attended by the candidate.
     */
    @Column(name = "CLASS_WEEKDAY", nullable = false)
    private int classWeekday;

    /**
     * Count of Philippine Martial Arts Association (PMAA) classes attended by the candidate.
     */
    @Column(name = "CLASS_PMAA", nullable = false)
    private int classPMAA;

    /**
     * Count of Olympic Sparring classes attended by the candidate
     */
    @Column(name = "CLASS_SPARRING", nullable = false)
    private int classSparring;

    /**
     * Count of MasterQuest classes attended by the candidate
     */
    @Column(name = "CLASS_MASTERQ", nullable = false)
    private int classMasterQ;

    /**
     * Count of DreamTeam classes attended by the candidate
     */
    @Column(name = "CLASS_DREAMTEAM", nullable = false)
    private int classDreamTeam;

    /**
     * Count of HyperPro classes attended by the candidate
     *
     * @since 1.0.0
     */
    @Column(name = "CLASS_HYPERPRO", nullable = false)
    private int classHyperPro;

    //
    // OTHER REQUIREMENTS
    //

    /**
     * Count of minutes of meditation spent by the candidate
     *
     * @since 2.0.0
     */
    @Column(name = "MEDITATION", nullable = false)
    private double meditation;

    /**
     * Count of Random Acts of Kindness (RAOK) performed by the candidate @since 1.0.0
     */
    @Column(name = "RAOK", nullable = false)
    private int randomActs;

    /**
     * Count of 30-minute sessions when the candidate mentors someone
     *
     * @since 1.0.0
     */
    @Column(name = "MENTOR", nullable = false)
    private int mentor;

    /**
     * Count of 30-minute sessions when the candidate is mentored by someone
     *
     * @since 1.0.0
     */
    @Column(name = "MENTEE", nullable = false)
    private int mentee;

    @Column(name = "LEADERSHIP", nullable = false)
    private int leadership;

    @Column(name = "LEADERSHIP2", nullable = false)
    private int leadership2;

    @Column(name = "JOURNALS", nullable = false)
    private int journals;

    public Requirements() {}

    public Requirements(double miles,
                        int pushUps,
                        int sitUps,
                        int burpees,
                        int kicks,
                        int poomsae,
                        int selfDefense,
                        double sparring,
                        double jumps,
                        int pullUps,
                        int planks,
                        int rollsFalls,
                        int classSaturday,
                        int classWeekday,
                        int classPMAA,
                        int classSparring,
                        int classMasterQ,
                        int classDreamTeam,
                        int classHyperPro,
                        double meditation,
                        int randomActs,
                        int mentor,
                        int mentee,
                        int leadership,
                        int leadership2) {
        this.miles = miles;
        this.pushUps = pushUps;
        this.sitUps = sitUps;
        this.burpees = burpees;
        this.kicks = kicks;
        this.poomsae = poomsae;
        this.selfDefense = selfDefense;
        this.sparring = sparring;
        this.jumps = jumps;
        this.pullUps = pullUps;
        this.planks = planks;
        this.rollsFalls = rollsFalls;
        this.classSaturday = classSaturday;
        this.classWeekday = classWeekday;
        this.classPMAA = classPMAA;
        this.classSparring = classSparring;
        this.classMasterQ = classMasterQ;
        this.classDreamTeam = classDreamTeam;
        this.classHyperPro = classHyperPro;
        this.meditation = meditation;
        this.randomActs = randomActs;
        this.mentor = mentor;
        this.mentee = mentee;
        this.leadership = leadership;
        this.leadership2 = leadership2;
    }

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

    public int getJournals() {
        return journals;
    }

    public void setJournals(int journals) {
        this.journals = journals;
    }
}
