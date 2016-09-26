package agency.tango.android.avatarview.example.view;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import agency.tango.android.avatarview.example.R;
import agency.tango.android.avatarview.example.databinding.ExampleActivityBinding;
import agency.tango.android.avatarview.example.viewmodel.ExampleViewModel;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarviewbindings.bindings.AvatarViewBindings;

public class ExampleActivity extends AppCompatActivity {

    private ExampleActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.example_activity, new ExampleDataComponent());
        binding.setViewModel(new ExampleViewModel());
    }

    private class ExampleDataComponent implements DataBindingComponent {
        public AvatarViewBindings getAvatarViewBindings() {
            return new AvatarViewBindings(new PicassoLoader());
        }
    }
}