package kubik.roman.moviesdb.fragments;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kubik.roman.moviesdb.GsonGetRequest;
import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.TmdbUrlBuilder;
import kubik.roman.moviesdb.activities.MovieDetailsActivity;
import kubik.roman.moviesdb.adapters.ImageListAdapter;
import kubik.roman.moviesdb.adapters.KnownAsListAdapter;
import kubik.roman.moviesdb.models.movie_details.Image;
import kubik.roman.moviesdb.models.movie_details.MovieDetails;
import kubik.roman.moviesdb.models.person_details.BasePersonInfo;
import kubik.roman.moviesdb.models.person_details.Cast;
import kubik.roman.moviesdb.models.person_details.MovieCreditsPerson;
import kubik.roman.moviesdb.models.person_details.PhotosPerson;
import kubik.roman.moviesdb.util.Validator;

/**
 * Created by roman on 5/11/2016.
 */
public class CastDetailsFragment extends Fragment implements Response.ErrorListener, View.OnClickListener {

    private static final String ID_TAG = "cast_id";

    private int mCastId;

    private View view;

    private ImageView mImvAvatar;

    private TextView mTvName;
    private TextView mTvDate;
    private TextView mTvPlace;
    private TextView mTvHomepage;
    private TextView mTvBiography;

    private RecyclerView mRvKnownFor;
    private RecyclerView mRvPhotos;

    private BasePersonInfo mBaseInfo;
    private MovieCreditsPerson mMovieCredits;
    private PhotosPerson mPhotosPerson;

    private List<Cast> mKnownAsList = new ArrayList<>();
    private List<Image> mPhotosList = new ArrayList<>();

    private RequestQueue queue;

    private MovieDetailsActivity mActivity;

    public static CastDetailsFragment newInstance(int castId) {
        CastDetailsFragment castDetailsFragment = new CastDetailsFragment();

        Bundle args = new Bundle();
        args.putInt(ID_TAG, castId);
        castDetailsFragment.setArguments(args);

        return castDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCastId = getArguments().getInt(ID_TAG);
        queue = Volley.newRequestQueue(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (MovieDetailsActivity) getActivity();

        view = inflater.inflate(R.layout.cast_details_fragment, container, false);

        initViews();
        getCastDetails();

        return view;
    }

    private void initViews() {
        mImvAvatar = (ImageView) view.findViewById(R.id.imv_avatar);

        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvDate = (TextView) view.findViewById(R.id.tv_dates);
        mTvPlace = (TextView) view.findViewById(R.id.tv_birth_place);
        mTvHomepage = (TextView) view.findViewById(R.id.tv_homepage);
        mTvBiography = (TextView) view.findViewById(R.id.tv_biography);

        mRvKnownFor = (RecyclerView) view.findViewById(R.id.rv_known_for);
        mRvPhotos = (RecyclerView) view.findViewById(R.id.rv_photos);

    }

    private void getCastDetails() {
        //Get BaseInfo
        GsonGetRequest<BasePersonInfo> requestBaseInfo = new GsonGetRequest<>(TmdbUrlBuilder.getBasePersonInfo(mCastId),
                BasePersonInfo.class, null, new Response.Listener<BasePersonInfo>() {
            @Override
            public void onResponse(BasePersonInfo response) {
                mBaseInfo = response;
                setupBaseInfo();
            }
        }, this);
        queue.add(requestBaseInfo);

        //Get Person movie casts
        GsonGetRequest<MovieCreditsPerson> requestCredits = new GsonGetRequest<>(TmdbUrlBuilder.getMovieCreditsPerson(mCastId),
                MovieCreditsPerson.class, null, new Response.Listener<MovieCreditsPerson>() {
            @Override
            public void onResponse(MovieCreditsPerson response) {
                mMovieCredits = response;
                setupCreditsInfo();
            }
        }, this);
        queue.add(requestCredits);

        //Get PersonPhoto
        GsonGetRequest<PhotosPerson> requestPhotos = new GsonGetRequest<>(TmdbUrlBuilder.getPersonPhotos(mCastId),
                PhotosPerson.class, null, new Response.Listener<PhotosPerson>() {
            @Override
            public void onResponse(PhotosPerson response) {
                mPhotosPerson = response;
                setupPhotos();
            }
        }, this);
        queue.add(requestPhotos);
    }

    private void setupBaseInfo() {
        Picasso.with(mActivity).load(TmdbUrlBuilder.getPosterBaseUrl(mBaseInfo.getProfilePath()))
                .fit().centerCrop().into(mImvAvatar);
        mTvName.setText(mBaseInfo.getName());
        String date = mBaseInfo.getBirthday() + " - ";
        if (Validator.isStringValid(mBaseInfo.getDeathday())) {
            date = date + mBaseInfo.getDeathday();
        } else {
            date = date + "...";
        }
        mTvDate.setText(date);
        mTvPlace.setText(mBaseInfo.getPlaceOfBirth());

        if (Validator.isStringValid(mBaseInfo.getHomepage())) {
            mTvHomepage.setText(mBaseInfo.getHomepage());
            mTvHomepage.setOnClickListener(this);
        } else {
            mTvHomepage.setText(R.string.no_homepage);
        }
        mTvHomepage.setPaintFlags(mTvHomepage.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mTvHomepage.setOnClickListener(this);

        mTvBiography.setText(mBaseInfo.getBiography());
    }

    private void setupCreditsInfo() {
        mKnownAsList.clear();
        mKnownAsList.addAll(mMovieCredits.getCast());

        KnownAsListAdapter adapter = new KnownAsListAdapter(mKnownAsList, mActivity);
        mRvKnownFor.setAdapter(adapter);
        mRvKnownFor.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.HORIZONTAL, false));

        adapter.SetOnItemClickListener(new KnownAsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int id = mKnownAsList.get(position).getId();
                MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(id);
                mActivity.forceLoadFragment(movieDetailsFragment);
            }
        });
    }

    private void setupPhotos() {
        mPhotosList.clear();
        mPhotosList.addAll(mPhotosPerson.getProfiles());
        ImageListAdapter imageAdapter = new ImageListAdapter(mPhotosList, getActivity(), false);
        mRvPhotos.setAdapter(imageAdapter);
        mRvPhotos.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));

        imageAdapter.SetOnItemClickListener(new ImageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GalleryDialog galleryDialog = GalleryDialog.newInstance(mPhotosList, position);
                galleryDialog.show(mActivity.getSupportFragmentManager(), GalleryDialog.class.getSimpleName());
            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String json;

        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {
                case 200:
                    break;
                default:
                    json = new String(response.data);
                    json = trimMessage(json, "status_message");
                    if (json != null) Toast.makeText(mActivity, json, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    public String trimMessage(String json, String key) {
        String trimmedString;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_homepage:
                break;
            default:
                break;
        }
    }
}
