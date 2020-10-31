package org.robolectric.shadows;

import static org.robolectric.shadow.api.Shadow.directlyOn;
import android.widget.NumberPicker;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

@Implements(value = NumberPicker.class)
public class ShadowNumberPicker extends ShadowLinearLayout {

    @RealObject
    private NumberPicker realNumberPicker;

    private int value;

    private int minValue;

    private int maxValue;

    private boolean wrapSelectorWheel;

    private String[] displayedValues;

    private NumberPicker.OnValueChangeListener onValueChangeListener;

    @Implementation
    protected void setValue(int value) {
        System.out.println("ShadowNumberPicker#setValue");
        this.value = value;
    }

    @Implementation
    protected int getValue() {
        System.out.println("ShadowNumberPicker#getValue");
        return value;
    }

    @Implementation
    protected void setDisplayedValues(String[] displayedValues) {
        System.out.println("ShadowNumberPicker#setDisplayedValues");
        this.displayedValues = displayedValues;
    }

    @Implementation
    protected String[] getDisplayedValues() {
        System.out.println("ShadowNumberPicker#getDisplayedValues");
        return displayedValues;
    }

    @Implementation
    protected void setMinValue(int minValue) {
        System.out.println("ShadowNumberPicker#setMinValue");
        this.minValue = minValue;
    }

    @Implementation
    protected void setMaxValue(int maxValue) {
        System.out.println("ShadowNumberPicker#setMaxValue");
        this.maxValue = maxValue;
    }

    @Implementation
    protected int getMinValue() {
        System.out.println("ShadowNumberPicker#getMinValue");
        return this.minValue;
    }

    @Implementation
    protected int getMaxValue() {
        System.out.println("ShadowNumberPicker#getMaxValue");
        return this.maxValue;
    }

    @Implementation
    protected void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        System.out.println("ShadowNumberPicker#setWrapSelectorWheel");
        this.wrapSelectorWheel = wrapSelectorWheel;
    }

    @Implementation
    protected boolean getWrapSelectorWheel() {
        System.out.println("ShadowNumberPicker#getWrapSelectorWheel");
        return wrapSelectorWheel;
    }

    @Implementation
    protected void setOnValueChangedListener(NumberPicker.OnValueChangeListener listener) {
        System.out.println("ShadowNumberPicker#setOnValueChangedListener");
        directlyOn(realNumberPicker, NumberPicker.class).setOnValueChangedListener(listener);
        this.onValueChangeListener = listener;
    }

    public NumberPicker.OnValueChangeListener getOnValueChangeListener() {
        return onValueChangeListener;
    }
}

