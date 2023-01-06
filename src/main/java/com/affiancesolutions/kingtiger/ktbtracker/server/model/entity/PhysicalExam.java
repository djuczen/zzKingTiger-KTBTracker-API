package com.affiancesolutions.kingtiger.ktbtracker.server.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PhysicalExam implements Serializable {

    private static final long serialVersionUID = -4800832183038873339L;

    @Column(name = "PRE_EXAM_RUN", nullable = false)

    private double preExamRun;

    @Column(name = "PRE_EXAM_PUSHUPS", nullable = false)
    private int preExamPushUps;

    @Column(name = "PRE_EXAM_SITUPS", nullable = false)
    private int preExamSitUps;

    @Column(name = "PRE_EXAM_BURPEES", nullable = false)
    private int preExamBurpees;

    @Column(name = "PRE_EXAM_PULLUPS", nullable = false)
    private int preExamPullUps;

    @Column(name = "PRE_EXAM_PLANKS", nullable = false)
    private int preExamPlanks;

    @Column(name = "EXAM_RUN", nullable = false)

    private double examRun;

    @Column(name = "EXAM_PUSHUPS", nullable = false)
    private int examPushUps;

    @Column(name = "EXAM_SITUPS", nullable = false)
    private int examSitUps;

    @Column(name = "EXAM_BURPEES", nullable = false)
    private int examBurpees;

    @Column(name = "EXAM_PULLUPS", nullable = false)
    private int examPullUps;

    @Column(name = "EXAM_PLANKS", nullable = false)
    private int examPlanks;

}
