package vectorwing.farmersdelight.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.effects.ComfortEffect;
import vectorwing.farmersdelight.registry.ModEffects;

@Mixin(Effect.class)
public class ComfortMixin
{
	/**
	 * This mixin makes Comfort defend the player from going under 3-5 hearts when under Poison or Wither.
	 * Credits to MoriyaShiine for writing most of it!
	 */
	@SuppressWarnings("ConstantConditions")
	@Inject(method = "performEffect", at = @At(value = "HEAD"), cancellable = true)
	private void performEffect(LivingEntity entity, int amplifier, CallbackInfo callbackInfo) {
		EffectInstance comfort = entity.getActivePotionEffect(ModEffects.COMFORT.get());
		if ((((Object) this) == Effects.POISON || ((Object) this) == Effects.WITHER) && comfort != null) {
			if (entity.getHealth() <= ComfortEffect.getHealthEndurance(comfort.getAmplifier())) {
				callbackInfo.cancel();
			}
		}
	}
}
