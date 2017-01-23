package dnkilic.anadoluajans;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dnkilic.anadoluajans.data.News;
import dnkilic.anadoluajans.view.Dialog;
import dnkilic.anadoluajans.view.DialogAdapter;
import dnkilic.anadoluajans.view.NewsAdapter;

public class MainActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements NewsResultListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private RecyclerView rvNews;
        private RecyclerView.Adapter adapter, errorDialogAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private ArrayList<News> dataset;
        private ArrayList<Dialog> errorDialogList;
        private String commonError = "Bir hata oluştu. Lütfen tekrar deneyiniz";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            rvNews = (RecyclerView) rootView.findViewById(R.id.rvNews);
            mLayoutManager = new LinearLayoutManager(getContext());
            rvNews.setLayoutManager(mLayoutManager);
            dataset = new ArrayList<>();
            adapter = new NewsAdapter(dataset, getActivity());
            rvNews.setAdapter(adapter);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    new RssFeedParser(this).execute("guncel");
                    break;
                case 1:
                    new RssFeedParser(this).execute("spor");
                    break;
                case 2:
                    new RssFeedParser(this).execute("ekonomi");
                    break;
                case 3:
                    new RssFeedParser(this).execute("turkiye");
                    break;
                case 4:
                    new RssFeedParser(this).execute("dunya");
                    break;
                case 5:
                    new RssFeedParser(this).execute("kultur-sanat");
                    break;
                case 6:
                    new RssFeedParser(this).execute("politika");
                    break;
                case 7:
                    new RssFeedParser(this).execute("bilim-teknoloji");
                    break;
                case 8:
                    new RssFeedParser(this).execute("yasam");
                    break;
                case 9:
                    new RssFeedParser(this).execute("saglik");
                    break;
                case 10:
                    new RssFeedParser(this).execute("analiz-haber");
                    break;
                case 11:
                    new RssFeedParser(this).execute("gunun-basliklari");
                    break;
            }

            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, ));

            return rootView;
        }

        @Override
        public void onSuccess(ArrayList<News> news) {
            for(News item : news)
            {
                dataset.add(item);
            }

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFail(boolean error, String errorMessage) {
            errorDialogList = new ArrayList<>();

            if (error){
                Dialog errorDialog = new Dialog(errorMessage);
                errorDialogList.add(errorDialog);
                errorDialogAdapter = new DialogAdapter(errorDialogList,getContext());
                rvNews.setAdapter(errorDialogAdapter);

            }else{
                Dialog errorDialog = new Dialog(commonError);
                errorDialogList.add(errorDialog);
                errorDialogAdapter = new DialogAdapter(errorDialogList,getContext());
                rvNews.setAdapter(errorDialogAdapter);
            }


        }


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 12;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "GÜNCEL";
                case 1:
                    return "SPOR";
                case 2:
                    return "EKONOMİ";
                case 3:
                    return "TÜRKİYE";
                case 4:
                    return "DÜNYA";
                case 5:
                    return "KÜLTÜR SANAT";
                case 6:
                    return "POLİTİKA";
                case 7:
                    return "BİLİM TEKNOLOJİ";
                case 8:
                    return "YAŞAM";
                case 9:
                    return "SAĞLIK";
                case 10:
                    return "ANALİZ HABER";
                case 11:
                    return "GÜNÜN BAŞLIKLARI";
            }
            return null;
        }
    }
}
