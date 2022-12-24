package com.affiancesolutions.kingtiger.ktbtracker.server.model.dto;

import com.affiancesolutions.kingtiger.ktbtracker.server.model.Requirements;
import jakarta.ws.rs.container.ResourceContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class Statistics implements Serializable {

    private static final double serialVersionUID = -79473790983979594L;

    private int candidateId;

    private LocalDate startDate;

    private LocalDate endDate;

    private double overall;

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

    private double journals;

    public Statistics() {
    }

    public Statistics(double miles,
                      double pushUps,
                      double sitUps,
                      double burpees,
                      double kicks,
                      double poomsae,
                      double selfDefense,
                      double sparring,
                      double jumps,
                      double pullUps,
                      double planks,
                      double rollsFalls,
                      double classSaturday,
                      double classWeekday,
                      double classPMAA,
                      double classSparring,
                      double classMasterQ,
                      double classDreamTeam,
                      double classHyperPro,
                      double meditation,
                      double randomActs,
                      double mentor,
                      double mentee,
                      double leadership,
                      double leadership2,
                      long journals) {
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
        this.journals = journals;
    }

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

    public double getOverall() {
        return overall;
    }

    public void setOverall(double overall) {
        this.overall = overall;
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

    public double getJournals() {
        return journals;
    }

    public void setJournals(double journals) {
        this.journals = journals;
    }


    public void setOverall(Requirements target) {
        setOverall(target, 1.0);
    }

    public void setOverall(Requirements target, double factor) {
        this.overall = calculateOverall(target, factor);
    }

    public void setPhysical(Requirements target, double factor) {
        this.overall = calculatePhysical(target, factor);
    }

    public void setClasses(Requirements target, double factor) {
        this.overall = calculateClasses(target, factor);
    }

    public void setOther(Requirements target, double factor) {
        this.overall = calculateOther(target, factor);
    }

    private double calculateOverall(Requirements target, double factor) {
        double overallValue = 0.0;
        double overallFactor = 0.0;

        overallValue += calculatePhysical(target, factor);
        overallFactor += 1.0;

        overallValue += calculateClasses(target, factor);
        overallFactor += 1.0;
        
        overallValue += calculateOther(target, factor);
        overallFactor += 1.0;
        
        return overallFactor > 0 ? overallValue / overallFactor : 0.0;
    }


    private double calculatePhysical(Requirements target, double factor) {
        double overallValue = 0.0;
        double overallFactor = 0.0;

        overallValue += target.getMiles() > 0 ?
                Math.min(getMiles() / (double) (target.getMiles() * factor), 1.0) : 0.0;
        overallFactor += target.getMiles() > 0 ? 1.0 : 0.0;

        overallValue += target.getPushUps() > 0 ?
                Math.min(getPushUps() / (double) (target.getPushUps() * factor), 1.0) : 0.0;
        overallFactor += target.getPushUps() > 0 ? 1.0 : 0.0;

        overallValue += target.getSitUps() > 0 ?
                Math.min(getSitUps() / (double) (target.getSitUps() * factor), 1.0) : 0.0;
        overallFactor += target.getSitUps() > 0 ? 1.0 : 0.0;

        overallValue += target.getBurpees() > 0 ?
                Math.min(getBurpees() / (double) (target.getBurpees() * factor), 1.0) : 0.0;
        overallFactor += target.getBurpees() > 0 ? 1.0 : 0.0;

        overallValue += target.getKicks() > 0 ?
                Math.min(getKicks() / (double) (target.getKicks() * factor), 1.0) : 0.0;
        overallFactor += target.getKicks() > 0 ? 1.0 : 0.0;

        overallValue += target.getPoomsae() > 0 ?
                Math.min(getPoomsae() / (double) (target.getPoomsae() * factor), 1.0) : 0.0;
        overallFactor += target.getPoomsae() > 0 ? 1.0 : 0.0;

        overallValue += target.getSelfDefense() > 0 ?
                Math.min(getSelfDefense() / (double) (target.getSelfDefense() * factor), 1.0) : 0.0;
        overallFactor += target.getSelfDefense() > 0 ? 1.0 : 0.0;

        overallValue += target.getSparring() > 0 ?
                Math.min(getSparring() / (double) (target.getSparring() * factor), 1.0) : 0.0;
        overallFactor += target.getSparring() > 0 ? 1.0 : 0.0;

        overallValue += target.getJumps() > 0 ?
                Math.min(getJumps() / (double) (target.getJumps() * factor), 1.0) : 0.0;
        overallFactor += target.getJumps() > 0 ? 1.0 : 0.0;

        overallValue += target.getPullUps() > 0 ?
                Math.min(getPullUps() / (double) (target.getPullUps() * factor), 1.0) : 0.0;
        overallFactor += target.getPullUps() > 0 ? 1.0 : 0.0;

        overallValue += target.getPlanks() > 0 ?
                Math.min(getPlanks() / (double) (target.getPlanks() * factor), 1.0) : 0.0;
        overallFactor += target.getPlanks() > 0 ? 1.0 : 0.0;

        overallValue += target.getRollsFalls() > 0 ?
                Math.min(getRollsFalls() / (double) (target.getRollsFalls() * factor), 1.0) : 0.0;
        overallFactor += target.getRollsFalls() > 0 ? 1.0 : 0.0;

        return overallFactor > 0 ? overallValue / overallFactor : 0.0;
    }

    private double calculateClasses(Requirements target, double factor) {
        double overallValue = 0.0;
        double overallFactor = 0.0;

        overallValue += target.getClassSaturday() > 0 ?
                Math.min(getClassSaturday() / (double) (target.getClassSaturday() * factor), 1.0) : 0.0;
        overallFactor += target.getClassSaturday() > 0 ? 1.0 : 0.0;

        overallValue += target.getClassWeekday() > 0 ?
                Math.min(getClassWeekday() / (double) (target.getClassWeekday() * factor), 1.0) : 0.0;
        overallFactor += target.getClassWeekday() > 0 ? 1.0 : 0.0;

        overallValue += target.getClassPMAA() > 0 ?
                Math.min(getClassPMAA() / (double) (target.getClassPMAA() * factor), 1.0) : 0.0;
        overallFactor += target.getClassPMAA() > 0 ? 1.0 : 0.0;

        overallValue += target.getClassSparring() > 0 ?
                Math.min(getClassSparring() / (double) (target.getClassSparring() * factor), 1.0) : 0.0;
        overallFactor += target.getClassSparring() > 0 ? 1.0 : 0.0;

        overallValue += target.getClassMasterQ() > 0 ?
                Math.min(getClassMasterQ() / (double) (target.getClassMasterQ() * factor), 1.0) : 0.0;
        overallFactor += target.getClassMasterQ() > 0 ? 1.0 : 0.0;

        overallValue += target.getClassDreamTeam() > 0 ?
                Math.min(getClassDreamTeam() / (double) (target.getClassDreamTeam() * factor), 1.0) : 0.0;
        overallFactor += target.getClassDreamTeam() > 0 ? 1.0 : 0.0;

        overallValue += target.getClassHyperPro() > 0 ?
                Math.min(getClassHyperPro() / (double) (target.getClassHyperPro() * factor), 1.0) : 0.0;
        overallFactor += target.getClassHyperPro() > 0 ? 1.0 : 0.0;

        return overallFactor > 0 ? overallValue / overallFactor : 0.0;
    }

    private double calculateOther(Requirements target, double factor) {
        double overallValue = 0.0;
        double overallFactor = 0.0;

        overallValue += target.getMeditation() > 0 ?
                Math.min(getMeditation() / (double) (target.getMeditation() * factor), 1.0) : 0.0;
        overallFactor += target.getMeditation() > 0 ? 1.0 : 0.0;

        overallValue += target.getRandomActs() > 0 ?
                Math.min(getRandomActs() / (double) (target.getRandomActs() * factor), 1.0) : 0.0;
        overallFactor += target.getRandomActs() > 0 ? 1.0 : 0.0;

        overallValue += target.getMentor() > 0 ?
                Math.min(getMentor() / (double) (target.getMentor() * factor), 1.0) : 0.0;
        overallFactor += target.getMentor() > 0 ? 1.0 : 0.0;

        overallValue += target.getMentee() > 0 ?
                Math.min(getMentee() / (double) (target.getMentee() * factor), 1.0) : 0.0;
        overallFactor += target.getMentee() > 0 ? 1.0 : 0.0;

        overallValue += target.getLeadership() > 0 ?
                Math.min(getLeadership() / (double) (target.getLeadership() * factor), 1.0) : 0.0;
        overallFactor += target.getLeadership() > 0 ? 1.0 : 0.0;

        overallValue += target.getLeadership2() > 0 ?
                Math.min(getLeadership2() / (double) (target.getLeadership2() * factor), 1.0) : 0.0;
        overallFactor += target.getLeadership2() > 0 ? 1.0 : 0.0;

        return overallFactor > 0 ? overallValue / overallFactor : 0.0;
    }

}
