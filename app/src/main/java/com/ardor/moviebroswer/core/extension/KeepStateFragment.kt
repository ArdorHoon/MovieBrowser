package com.ardor.moviebroswer.core.extension

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("keep_state_fragment")
class KeepStateFragment(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()

        val transaction = manager.beginTransaction()

        var initialNavigate = false
        val currentFragment = manager.primaryNavigationFragment

        if (currentFragment != null) {
            transaction.hide(currentFragment)
        } else {
            initialNavigate = true
        }

        var fragment = manager.findFragmentByTag(tag)

        if (fragment == null) {
//            add로 fragment 최초 생성 (add)
            val className = destination.className
            fragment = manager.fragmentFactory.instantiate(context.classLoader, className)
            transaction.add(containerId, fragment, tag)
        } else {
            transaction.show(fragment)
        }

//        destination fragment를 primary로 설정
        transaction.setPrimaryNavigationFragment(fragment)

//        transaction 관련 fragment 상태 변경 최적화
        transaction.setReorderingAllowed(true)
        transaction.commitNow()

        return if (initialNavigate) {
            destination
        } else {
            null
        }
    }
}