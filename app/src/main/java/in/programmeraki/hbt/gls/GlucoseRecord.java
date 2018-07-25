/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package in.programmeraki.hbt.gls;

import java.util.Calendar;

public class GlucoseRecord {
	public static final int UNIT_kgpl = 0;
	public static final int UNIT_molpl = 1;

	/** Record sequence number */
	protected int sequenceNumber;
	/** The base time of the measurement */
	protected Calendar time;
	/** Time offset of the record */
	protected int timeOffset;
	/** The glucose concentration. 0 if not present */
	protected float glucoseConcentration;
	/** Concentration unit. One of the following: {@link GlucoseRecord#UNIT_kgpl}, {@link GlucoseRecord#UNIT_molpl} */
	protected int unit;
	/** The type of the record. 0 if not present */
	protected int type;
	/** The sample location. 0 if unknown */
	protected int sampleLocation;
	/** Sensor status annunciation flags. 0 if not present */
	protected int status;

	protected MeasurementContext context;

	public static class MeasurementContext {
		public static final int UNIT_kg = 0;
		public static final int UNIT_l = 1;

		/**
		 * One of the following:<br/>
		 * 0 Not present<br/>
		 * 1 Breakfast<br/>
		 * 2 Lunch<br/>
		 * 3 Dinner<br/>
		 * 4 Snack<br/>
		 * 5 Drink<br/>
		 * 6 Supper<br/>
		 * 7 Brunch
		 */
		protected int carbohydrateId;
		/** Number of kilograms of carbohydrate */
		protected float carbohydrateUnits;
		/**
		 * One of the following:<br/>
		 * 0 Not present<br/>
		 * 1 Preprandial (before meal)<br/>
		 * 2 Postprandial (after meal)<br/>
		 * 3 Fasting<br/>
		 * 4 Casual (snacks, drinks, etc.)<br/>
		 * 5 Bedtime
		 */
		protected int meal;
		/**
		 * One of the following:<br/>
		 * 0 Not present<br/>
		 * 1 Self<br/>
		 * 2 Health Care Professional<br/>
		 * 3 Lab test<br/>
		 * 15 Tester value not available
		 */
		protected int tester;
		/**
		 * One of the following:<br/>
		 * 0 Not present<br/>
		 * 1 Minor health issues<br/>
		 * 2 Major health issues<br/>
		 * 3 During menses<br/>
		 * 4 Under stress<br/>
		 * 5 No health issues<br/>
		 * 15 Tester value not available
		 */
		protected int health;
		/** Exercise duration in seconds. 0 if not present */
		protected int exerciseDuration;
		/** Exercise intensity in percent. 0 if not present */
		protected int exerciseIntensity;
		/**
		 * One of the following:<br/>
		 * 0 Not present<br/>
		 * 1 Rapid acting insulin<br/>
		 * 2 Short acting insulin<br/>
		 * 3 Intermediate acting insulin<br/>
		 * 4 Long acting insulin<br/>
		 * 5 Pre-mixed insulin
		 */
		protected int medicationId;
		/** Quantity of medication. See {@link #medicationUnit} for the unit. */
		protected float medicationQuantity;
		/** One of the following: {@link GlucoseRecord.MeasurementContext#UNIT_kg}, {@link GlucoseRecord.MeasurementContext#UNIT_l}. */
		protected int medicationUnit;
		/** HbA1c value. 0 if not present */
		protected float HbA1c;
	}
}
