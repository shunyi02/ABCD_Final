package group.assignment.abcdfinal.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import group.assignment.abcdfinal.fragments.Add;
import group.assignment.abcdfinal.fragments.Home;
import group.assignment.abcdfinal.fragments.Notification;
import group.assignment.abcdfinal.fragments.Profile;
import group.assignment.abcdfinal.fragments.Search;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int noOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        noOfTabs = tabCount;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new Home();

            case 1:
                return new Search();

            case 2:
                return new Add();

            case 3:
                return new Notification();
            case 4:
                return new Profile();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
