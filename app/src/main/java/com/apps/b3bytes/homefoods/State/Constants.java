package com.apps.b3bytes.homefoods.State;

/**
 * Created by sindhu on 9/20/2015.
 */
public class Constants {
    // These identifiers are used to communicate from fragment to activity.
    // there will be a common callback between fragment and activity
    // which fragment is calling and whats the purpose of callback will
    // be known by these identifiers. The common interface will be
    // fragmentActionRequestHandler(fragment_id, action_id, bundle);

    // ChefDishEditInfoFragment IDs
    public static final int FRAGMENT_ChefDishEditInfoFragment_ID = 0;
    public static final int ACTION_NEXT_ChefDishEditInfoFragment_ID = 0;
    public static final int ACTION_SAVE_ChefDishEditInfoFragment_ID = 1;
    public static final int ACTION_CANCEL_ChefDishEditInfoFragment_ID = 2;
    public static final int ACTION_HOMEUP_ChefDishEditInfoFragment_ID = 3;
    // ChefDishEditPriceFragment IDs
    public static final int FRAGMENT_ChefDishEditPriceFragment_ID = 1;
    public static final int ACTION_NEXT_ChefDishEditPriceFragment_ID = 0;
    public static final int ACTION_BACK_ChefDishEditPriceFragment_ID = 1;
    public static final int ACTION_SAVE_ChefDishEditPriceFragment_ID = 2;
    public static final int ACTION_CANCEL_ChefDishEditPriceFragment_ID = 3;
    public static final int ACTION_HOMEUP_ChefDishEditPriceFragment_ID = 4;
    // ChefDishEditAvailFragment IDs
    public static final int FRAGMENT_ChefDishEditAvailFragment_ID = 2;
    public static final int ACTION_NEXT_ChefDishEditAvailFragment_ID = 0;
    public static final int ACTION_BACK_ChefDishEditAvailFragment_ID = 1;
    public static final int ACTION_SAVE_ChefDishEditAvailFragment_ID = 2;
    public static final int ACTION_CANCEL_ChefDishEditAvailFragment_ID = 3;
    public static final int ACTION_HOMEUP_ChefDishEditAvailFragment_ID = 4;
    // ChefDishEditImageFragment IDs
    public static final int FRAGMENT_ChefDishEditImageFragment_ID = 3;
    public static final int ACTION_BACK_ChefDishEditImageFragment_ID = 0;
    public static final int ACTION_SAVE_ChefDishEditImageFragment_ID = 1;
    public static final int ACTION_CANCEL_ChefDishEditImageFragment_ID = 2;
    public static final int ACTION_HOMEUP_ChefDishEditImageFragment_ID = 3;
    // ChefMenuFragment IDs
    public static final int FRAGMENT_ChefMenuFragment_ID = 4;
    public static final int ACTION_DISH_ITEM_CLICK_ChefMenuFragment_ID = 0;
    public static final int ACTION_DISH_ADD_ChefMenuFragment_ID = 1;
    // ChefDishReadonlyFragment IDs
    public static final int FRAGMENT_ChefDishReadonlyFragment_ID = 5;
    public static final int ACTION_EDIT_ChefDishReadonlyFragment_ID = 0;
    public static final int ACTION_HOMEUP_ChefDishReadonlyFragment_ID = 1;
    // FoodieHomeFragment IDs
    public static final int FRAGMENT_FoodieHomeFragment_ID = 6;
    public static final int ACTION_CHECKOUT_CART_FoodieHomeFragment_ID = 0;
    public static final int ACTION_DISH_DESC_FoodieHomeFragment_ID = 1;
    public static final int ACTION_DISH_REVIEW_FoodieHomeFragment_ID = 2;
    // DishDescFragment IDs
    public static final int FRAGMENT_DishDescFragment_ID = 7;
    public static final int ACTION_CHECKOUT_CART_DishDescFragment_ID = 0;
    public static final int ACTION_DISH_REVIEW_DishDescFragment_ID = 1;
    public static final int ACTION_CHEF_REVIEW_DishDescFragment_ID = 2;
    public static final int ACTION_HOMEUP_DishDescFragment_ID = 3;
    // DishReviewFragment IDs
    public static final int FRAGMENT_DishReviewFragment_ID = 8;
    public static final int ACTION_HOMEUP_DishReviewFragment_ID = 0;
    // FoodieCartFragment IDs
    public static final int FRAGMENT_FoodieCartFragment_ID = 9;
    public static final int ACTION_PROCEED_PAYMENT_FoodieCartFragment_ID = 0;
    public static final int ACTION_HOMEUP_FoodieCartFragment_ID = 1;
    // FoodieCheckoutFragment IDs
    public static final int FRAGMENT_FoodieCheckoutFragment_ID = 10;
    public static final int ACTION_ADD_CARD_FoodieCheckoutFragment_ID = 0;
    public static final int ACTION_PLACE_ORDER_FoodieCheckoutFragment_ID = 1;
    public static final int ACTION_HOMEUP_FoodieCheckoutFragment_ID = 2;
    // FoodieAddPaymentCardFragment IDs
    public static final int FRAGMENT_FoodieAddPaymentCardFragment_ID = 11;
    public static final int ACTION_SAVE_CARD_FoodieAddPaymentCardFragment_ID = 0;
    public static final int ACTION_ADD_BILLING_ADDRESS_FoodieAddPaymentCardFragment_ID = 1;
    public static final int ACTION_HOMEUP_FoodieAddPaymentCardFragment_ID = 2;
    // FoodieAddBillingAddressFragment IDs
    public static final int FRAGMENT_FoodieAddBillingAddressFragment_ID = 12;
    public static final int ACTION_SAVE_BILLING_ADDRESS_FoodieAddPaymentCardFragment_ID = 0;
    public static final int ACTION_HOMEUP_FoodieAddBillingAddressFragment_ID = 1;
    // FoodiePastOrdersTabFragment IDs
    public static final int FRAGMENT_FoodiePastOrdersTabFragment_ID = 13;
    public static final int ACTION_ORDER_DETAILS_FoodiePastOrdersTabFragment_ID = 0;
    public static final int ACTION_BUY_DISH_AGAIN_FoodiePastOrdersTabFragment_ID = 1;
    public static final int ACTION_WRITE_DISH_REVIEW_FoodiePastOrdersTabFragment_ID = 2;
    // FoodiePendingOrdersTabFragment IDs
    public static final int FRAGMENT_FoodiePendingOrdersTabFragment_ID = 14;
    public static final int ACTION_CANCEL_ORDER_FoodiePendingOrdersTabFragment_ID = 0;
    // FoodieViewPastPendingOrderDetailsFragment IDs
    public static final int FRAGMENT_FoodieViewPastPendingOrderDetailsFragment_ID = 15;
    public static final int ACTION_CANCEL_ORDER_FoodieViewPastPendingOrderDetailsFragment_ID = 0;
    public static final int ACTION_BUY_DISH_AGAIN_FoodieViewPastPendingOrderDetailsFragment_ID = 1;
    public static final int ACTION_WRITE_DISH_REVIEW_FoodieViewPastPendingOrderDetailsFragment_ID = 2;
    public static final int ACTION_HOMEUP_FoodieViewPastPendingOrderDetailsFragment_ID = 3;
    // FoodieGiveDishReviewFragment IDs
    public static final int FRAGMENT_FoodieGiveDishReviewFragment_ID = 16;
    public static final int ACTION_HOMEUP_FoodieGiveDishReviewFragment_ID = 0;
    // ChefReviewFragment IDs
    public static final int FRAGMENT_ChefReviewFragment_ID = 17;
    public static final int ACTION_HOMEUP_ChefReviewFragment_ID = 0;
    // ChefReviewFragment IDs
    public static final int FRAGMENT_RegisterNameFragment_ID = 18;
    public static final int ACTION_REGISTER_RegisterNameFragment_ID = 0;
    public static final int ACTION_CANCEL_RegisterNameFragment_ID = 1;
    public static final int ACTION_HOMEUP_RegisterNameFragment_ID = 2;
    // LoginFragment IDs
    public static final int FRAGMENT_LoginFragment_ID = 19;
    public static final int ACTION_SIGN_IN_LoginFragment_ID = 0;
    public static final int ACTION_REGISTER_LoginFragment_ID = 1;
    public static final int ACTION_HOMEUP_LoginFragment_ID = 2;
}
