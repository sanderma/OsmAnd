package net.osmand.plus.settings.enums;

import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import net.osmand.plus.R;

public enum WidgetSize {

	SMALL(R.string.rendering_value_small_name, R.drawable.ic_action_item_size_s, R.dimen.simple_widget_small_height),
	MEDIUM(R.string.rendering_value_medium_w_name, R.drawable.ic_action_item_size_m, R.dimen.simple_widget_medium_height),
	LARGE(R.string.shared_string_large, R.drawable.ic_action_item_size_l, R.dimen.simple_widget_large_height);

	@StringRes
	public final int titleId;
	@DrawableRes
	public final int iconId;
	@DimenRes
	public final int heightDimenId;

	WidgetSize(@StringRes int titleId, @DrawableRes int iconId, @DimenRes int heightDimenId) {
		this.titleId = titleId;
		this.iconId = iconId;
		this.heightDimenId = heightDimenId;
	}

	@StringRes
	public int getTitleId() {
		return titleId;
	}

	@DrawableRes
	public int getIconId() {
		return iconId;
	}
}
