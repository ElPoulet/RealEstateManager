package com.openclassrooms.realestatemanager.fragment.list.embedding;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;


import androidx.window.embedding.ActivityFilter;
import androidx.window.embedding.ActivityRule;
import androidx.window.embedding.RuleController;
import androidx.window.embedding.SplitAttributes;
import androidx.window.embedding.SplitPairFilter;
import androidx.window.embedding.SplitPairRule;
import androidx.window.embedding.SplitPlaceholderRule;
import androidx.window.embedding.SplitRule;

import com.openclassrooms.realestatemanager.fragment.list.AddAppartmentActivity;
import com.openclassrooms.realestatemanager.fragment.list.AppartmentFragment;
import com.openclassrooms.realestatemanager.fragment.list.DetailApartment;
import com.openclassrooms.realestatemanager.fragment.list.EditApartmentActivity;
import com.openclassrooms.realestatemanager.fragment.list.FilterMapFragment;
import com.openclassrooms.realestatemanager.fragment.list.MainActivity;
import com.openclassrooms.realestatemanager.fragment.list.MapFragment;
import com.openclassrooms.realestatemanager.fragment.list.Simulator;

import java.util.HashSet;
import java.util.Set;

public class SplitManager {
    static void createSplit(Context context) {

        SplitPairFilter splitPairFilter = new SplitPairFilter(
                new ComponentName(context, AppartmentFragment.class),
                new ComponentName(context, DetailApartment.class),
                null
        );

        Set<SplitPairFilter> filterSet = new HashSet<>();
        filterSet.add(splitPairFilter);

        SplitAttributes splitAttributes = new SplitAttributes.Builder()
                .setSplitType(SplitAttributes.SplitType.ratio(0.40f))
                .setLayoutDirection(SplitAttributes.LayoutDirection.LEFT_TO_RIGHT)
                .build();

        SplitPairRule splitPairRule = new SplitPairRule.Builder(filterSet)
                .setDefaultSplitAttributes(splitAttributes)
                .setMinWidthDp(840)
                .setMinSmallestWidthDp(600)
                .setFinishPrimaryWithSecondary(SplitRule.FinishBehavior.NEVER)
                .setFinishSecondaryWithPrimary(SplitRule.FinishBehavior.ALWAYS)
                .setClearTop(false)
                .build();

        RuleController ruleController = RuleController.getInstance(context);
        ruleController.addRule(splitPairRule);

        ActivityFilter placeholderActivityFilter = new ActivityFilter(
                new ComponentName(context, AppartmentFragment.class),
                null
        );

        Set<ActivityFilter> placeholderActivityFilterSet = new HashSet<>();
        placeholderActivityFilterSet.add(placeholderActivityFilter);

        SplitPlaceholderRule splitPlaceholderRule = new SplitPlaceholderRule.Builder(
                placeholderActivityFilterSet,
                new Intent(context, PlaceholderActivity.class)
        ).setDefaultSplitAttributes(splitAttributes)
                .setMinWidthDp(840)
                .setMinSmallestWidthDp(600)
                .setFinishPrimaryWithPlaceholder(SplitRule.FinishBehavior.ALWAYS)
                .build();

        ruleController.addRule(splitPlaceholderRule);

        ruleController.addRule(splitPlaceholderRule);

        //FILTER EDIT APARTMENT//

        ActivityFilter EditApartmentActivityFilter = new ActivityFilter(
                new ComponentName(context, EditApartmentActivity.class),
                null
        );
        Set<ActivityFilter> EditApartmentActivityFilterSet = new HashSet<>();
        EditApartmentActivityFilterSet.add(EditApartmentActivityFilter);
        ActivityRule activityRule = new ActivityRule.Builder(
                EditApartmentActivityFilterSet
        ).setAlwaysExpand(true).build();
        ruleController.addRule(activityRule);

        //FILTER ADD APARTMENT//

        ActivityFilter AddAppartmentActivityFilter = new ActivityFilter(
                new ComponentName(context, AddAppartmentActivity.class),
                null
        );
        Set<ActivityFilter> AddAppartmentActivityFilterSet = new HashSet<>();
        EditApartmentActivityFilterSet.add(AddAppartmentActivityFilter);
        ActivityRule activityRuleTwo = new ActivityRule.Builder(
                AddAppartmentActivityFilterSet
        ).setAlwaysExpand(true).build();
        ruleController.addRule(activityRuleTwo);

        //FILTER MAP FRAGMENT//
        ActivityFilter MapFragmentActivityFilter = new ActivityFilter(
                new ComponentName(context, MapFragment.class),
                null
        );
        Set<ActivityFilter> MapFragmentActivityFilterSet = new HashSet<>();
        EditApartmentActivityFilterSet.add(MapFragmentActivityFilter);
        ActivityRule activityRuleThree = new ActivityRule.Builder(
                MapFragmentActivityFilterSet
        ).setAlwaysExpand(true).build();
        ruleController.addRule(activityRuleThree);

        //FILTER FILTER MAP FRAGMENT//

        ActivityFilter FilterMapFragmentActivityFilter = new ActivityFilter(
                new ComponentName(context, FilterMapFragment.class),
                null
        );
        Set<ActivityFilter> FilterMapFragmentActivityFilterSet = new HashSet<>();
        EditApartmentActivityFilterSet.add(FilterMapFragmentActivityFilter);
        ActivityRule activityRuleFour = new ActivityRule.Builder(
                FilterMapFragmentActivityFilterSet
        ).setAlwaysExpand(true).build();
        ruleController.addRule(activityRuleFour);

        //FILTER MAIN ACTIVITY//

        ActivityFilter MainActivityFilter = new ActivityFilter(
                new ComponentName(context, MainActivity.class),
                null
        );
        Set<ActivityFilter> MainActivityFilterSet = new HashSet<>();
        EditApartmentActivityFilterSet.add(MainActivityFilter);
        ActivityRule activityRuleFive = new ActivityRule.Builder(
                MainActivityFilterSet
        ).setAlwaysExpand(true).build();
        ruleController.addRule(activityRuleFive);

        //FILTER SIMULATOR//

        ActivityFilter SimulatorFilter = new ActivityFilter(
                new ComponentName(context, Simulator.class),
                null
        );
        Set<ActivityFilter> SimulatorFilterSet = new HashSet<>();
        EditApartmentActivityFilterSet.add(SimulatorFilter);
        ActivityRule activityRuleSix = new ActivityRule.Builder(
                SimulatorFilterSet
        ).setAlwaysExpand(true).build();
        ruleController.addRule(activityRuleSix);

    }
}
