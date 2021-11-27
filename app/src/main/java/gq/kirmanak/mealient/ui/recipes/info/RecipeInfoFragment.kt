package gq.kirmanak.mealient.ui.recipes.info

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import gq.kirmanak.mealient.R
import gq.kirmanak.mealient.databinding.FragmentRecipeInfoBinding
import timber.log.Timber

@AndroidEntryPoint
class RecipeInfoFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRecipeInfoBinding? = null
    private val binding: FragmentRecipeInfoBinding
        get() = checkNotNull(_binding) { "Binding requested when fragment is off screen" }
    private val arguments by navArgs<RecipeInfoFragmentArgs>()
    private val viewModel by viewModels<RecipeInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.v("onCreateView() called with: inflater = $inflater, container = $container, savedInstanceState = $savedInstanceState")
        _binding = FragmentRecipeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v("onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState")

        binding.ingredientsList.adapter = viewModel.recipeIngredientsAdapter
        binding.instructionsList.adapter = viewModel.recipeInstructionsAdapter

        viewModel.loadRecipeImage(binding.image, arguments.recipeSlug)
        viewModel.loadRecipeInfo(arguments.recipeId, arguments.recipeSlug)

        viewModel.recipeInfo.observe(viewLifecycleOwner) {
            Timber.d("onViewCreated: full info $it")
            binding.title.text = it.recipeSummaryEntity.name
            binding.description.text = it.recipeSummaryEntity.description
        }
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), R.style.NoShapeBottomSheetDialog)

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.v("onDestroyView() called")
        _binding = null
    }
}