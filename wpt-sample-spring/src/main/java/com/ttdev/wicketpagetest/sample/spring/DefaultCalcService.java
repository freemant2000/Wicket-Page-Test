package com.ttdev.wicketpagetest.sample.spring;

public class DefaultCalcService implements CalcService {

	public int calcNext(int current) {
		return current*2;
	}


}
