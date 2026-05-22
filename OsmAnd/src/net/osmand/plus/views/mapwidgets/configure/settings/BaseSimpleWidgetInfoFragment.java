package net.osmand.plus.views.mapwidgets.configure.settings;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import net.osmand.plus.R;
import net.osmand.plus.helpers.AndroidUiHelper;
import net.osmand.plus.settings.backend.preferences.CommonPreference;
import net.osmand.plus.views.mapwidgets.widgets.SimpleWidget;

public class BaseSimpleWidgetInfoFragment extends BaseResizableWidgetSettingFragment {
	private static final String SHOW_ICON_KEY = "show_icon_key";
	private static final String DYNAMIC_HEIGHT_KEY = "dynamic_height_key";

	public CommonPreference<Boolean> shouldShowIconPref;
	public CommonPreference<Boolean> dynamicHeightPref;

	private boolean showIcon;
	private boolean dynamicHeight;
	private View showIconContainer;

	@Override
	protected void initParams(@NonNull Bundle bundle) {
		super.initParams(bundle);
		if (widgetInfo != null && widgetInfo.widget instanceof SimpleWidget simpleWidget) {
			shouldShowIconPref = simpleWidget.shouldShowIconPref();
			showIcon = bundle.containsKey(SHOW_ICON_KEY) ? bundle.getBoolean(SHOW_ICON_KEY) : shouldShowIconPref.get();
			dynamicHeightPref = simpleWidget.getDynamicHeightPref();
			dynamicHeight = bundle.containsKey(DYNAMIC_HEIGHT_KEY) ? bundle.getBoolean(DYNAMIC_HEIGHT_KEY) : dynamicHeightPref.get();
		}
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(SHOW_ICON_KEY, showIcon);
		outState.putBoolean(DYNAMIC_HEIGHT_KEY, dynamicHeight);
	}

	@Override
	protected void setupTopContent(@NonNull ViewGroup container) {
		super.setupTopContent(container);
		inflate(R.layout.simple_widget_settings, container);

		SwitchCompat switchCompat = container.findViewById(R.id.show_icon_toggle);
		switchCompat.setChecked(showIcon);
		showIconContainer = container.findViewById(R.id.show_icon_container);
		showIconContainer.setOnClickListener(v -> updateShowIcon(!showIcon, switchCompat));
		showIconContainer.setBackground(getPressedStateDrawable());
		updateShowIconContainerVisibility();

		if (dynamicHeightPref != null) {
			inflate(R.layout.simple_widget_dynamic_height_setting, container);
			SwitchCompat dynamicHeightSwitch = container.findViewById(R.id.dynamic_height_toggle);
			dynamicHeightSwitch.setChecked(dynamicHeight);
			View dynamicHeightContainer = container.findViewById(R.id.dynamic_height_container);
			dynamicHeightContainer.setOnClickListener(v -> {
				dynamicHeight = !dynamicHeight;
				dynamicHeightSwitch.setChecked(dynamicHeight);
			});
			dynamicHeightContainer.setBackground(getPressedStateDrawable());
		}
	}

	private void updateShowIconContainerVisibility() {
		AndroidUiHelper.updateVisibility(showIconContainer, !isSmallHeight() || isVerticalPanel);
	}

	@Override
	protected void onWidgetSizeChanged() {
		super.onWidgetSizeChanged();
		updateShowIconContainerVisibility();
	}

	private void updateShowIcon(boolean shouldShowIcon, SwitchCompat switchCompat) {
		switchCompat.setChecked(shouldShowIcon);
		showIcon = shouldShowIcon;
	}

	@Override
	protected void applySettings() {
		shouldShowIconPref.set(showIcon);
		if (dynamicHeightPref != null) {
			dynamicHeightPref.set(dynamicHeight);
		}
		if (widgetInfo != null) {
			if (widgetInfo.widget instanceof SimpleWidget simpleWidget) {
				simpleWidget.updateWidgetView();
			}
		}
		super.applySettings();
	}
}
