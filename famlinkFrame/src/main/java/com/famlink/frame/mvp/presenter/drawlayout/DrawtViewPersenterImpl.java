package com.famlink.frame.mvp.presenter.drawlayout;


import com.famlink.frame.R;

/**
 * Created by wangkai on 16/6/15.
 */
public class DrawtViewPersenterImpl implements DrawPresenter {
    private DrawViewInterface mDrawView;

    public DrawtViewPersenterImpl(DrawViewInterface mDrawView) {
        this.mDrawView = mDrawView;
    }
    @Override
    public void switchNavigation(int id) {
//        if(id == R.id.navigation_group_1){
//            mDrawView.switchNews();
//        }else if(id == R.id.navigation_group_2){
//            mDrawView.switchImages();
//        }else if(id == R.id.navigation_group_3){
//            mDrawView.switchWeather();
//        }else if(id == R.id.navigation_group_4) {
//            mDrawView.switchAbout();
//        }else if(id == R.id.navigation_menu_1){
//            mDrawView.switchsetting();
//        }else if(id == R.id.navigation_menu_2){
//            mDrawView.switchAbout();
//        }else{
//            mDrawView.switchNews();
//        }
//        switch (id) {
//            case R.id.navigation_group_1:
//                mDrawView.switchNews();
//                break;
//            case R.id.navigation_group_2:
//                mDrawView.switchImages();
//                break;
//            case R.id.navigation_group_3:
//                mDrawView.switchWeather();
//                break;
//            case R.id.navigation_group_4:
//                mDrawView.switchAbout();
//                break;
//            case R.id.navigation_menu_1:
//                mDrawView.switchsetting();
//                break;
//            case R.id.navigation_menu_2:
//                mDrawView.switchAbout();
//                break;
//            default:
//                mDrawView.switchNews();
//                break;
//        }
    }
}
