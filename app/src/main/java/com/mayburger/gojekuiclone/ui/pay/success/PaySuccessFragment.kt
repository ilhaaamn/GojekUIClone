package com.mayburger.gojekuiclone.ui.pay.success

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mayburger.gojekuiclone.BR
import com.mayburger.gojekuiclone.R
import com.mayburger.gojekuiclone.databinding.FragmentPaySuccessBinding
import com.mayburger.gojekuiclone.ui.base.BaseFragment
import com.mayburger.gojekuiclone.ui.pay.success.PaySuccessViewModel.Companion.playSuccessAnimation
import com.mayburger.gojekuiclone.util.ext.addTransitionListener
import com.mayburger.gojekuiclone.util.ext.onTransitionProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_food_order.*


@AndroidEntryPoint
class PaySuccessFragment : BaseFragment<FragmentPaySuccessBinding, PaySuccessViewModel>(),
        PaySuccessNavigator {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_pay_success
    override val viewModel: PaySuccessViewModel by viewModels()

    var isAnimating = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigator = this
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        motionLayout.onTransitionProgress {progress,end->
            if (progress > 0.7 && !isAnimating) {
                isAnimating = true
                playSuccessAnimation()
            }
        }
    }

    override fun onBackPressed(fragment: Fragment) {
        motionLayout.setTransition(R.id.endToFinish)
        motionLayout.transitionToEnd()
        motionLayout.addTransitionListener(onEnd={ _, p1->
            if (p1 == R.id.finish) {
                if (!viewModel.isAnimating) {
                    requireActivity().supportFragmentManager.beginTransaction().remove(fragment)
                            .commit()
                }
            }
        })
    }

}