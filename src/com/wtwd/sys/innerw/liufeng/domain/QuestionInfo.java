package com.wtwd.sys.innerw.liufeng.domain;

import java.io.Serializable;

import com.godoing.rose.http.PublicVoBean;

public class QuestionInfo extends PublicVoBean implements Serializable {
	private static final long serialVersionUID = 5493326832031697257L;

	private Integer id;

    private String question_type;

    private String question_title;
    
    private String question_content;

    public String getQuestion_type() {
		return question_type;
	}

	public void setQuestion_type(String question_type) {
		this.question_type = question_type;
	}

	public String getQuestion_title() {
		return question_title;
	}

	public void setQuestion_title(String question_title) {
		this.question_title = question_title;
	}

	public String getQuestion_content() {
		return question_content;
	}

	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}