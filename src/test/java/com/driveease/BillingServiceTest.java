package com.driveease;

import com.driveease.model.Car;
import com.driveease.model.VehicleStatus;
import com.driveease.service.BillingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillingServiceTest {

    @Test
    void carRentalCostShouldBeCalculatedCorrectly() {
        Car car = new Car(
                1,
                "TEST-001",
                "Toyota",
                "Test",
                1000,
                VehicleStatus.AVAILABLE,
                5
        );

        assertEquals(3000, car.calculateRentalCost(3));
    }

    @Test
    void lateFeeShouldBeTwentyPercentPerLateDay() {
        Car car = new Car(
                1,
                "TEST-001",
                "Toyota",
                "Test",
                1000,
                VehicleStatus.AVAILABLE,
                5
        );

        BillingService billingService = new BillingService();

        assertEquals(400, billingService.calculateLateFee(car, 2));
    }
}
