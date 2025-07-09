package com.openclassrooms.realestatemanager;


import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class UtilsTest {

    @Test
    public void convert200DollarsToEuroTest(){
        Utils myUtils = new Utils();
        String result = String.valueOf(myUtils.convertDollarToEuro(200));
        assertThat(result).isEqualTo("180,34");

    }







}
