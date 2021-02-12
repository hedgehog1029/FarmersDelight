package vectorwing.farmersdelight.effects;

import com.google.common.collect.Sets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.registry.ModEffects;

import java.util.Set;

@SuppressWarnings("unused")
public class ComfortEffect extends Effect
{
	public static final Set<Effect> COMFORT_IMMUNITIES = Sets.newHashSet(Effects.SLOWNESS, Effects.WEAKNESS, Effects.HUNGER);

	/**
	 * This effect safeguards a few hearts from being damaged by Poison or Wither, depending in the effect level.
	 * The base protection is 3 hearts, with each amplifier level defending 2 more.
	 * Most of the logic is applied on the equivalent mixin class; this class handles effect info and the overlay.
	 */
	public ComfortEffect() {
		super(EffectType.BENEFICIAL, 0);
	}

	public static int getHealthEndurance(int amplifier) {
		return (amplifier + 1) * 4 + 2;
	}

	@Mod.EventBusSubscriber(modid = FarmersDelight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class ComfortEvent
	{
		@SubscribeEvent
		public static void onComfortDuration(PotionEvent.PotionApplicableEvent event) {
			EffectInstance effect = event.getPotionEffect();
			LivingEntity entity = event.getEntityLiving();
			if (entity.getActivePotionEffect(ModEffects.COMFORT.get()) != null && COMFORT_IMMUNITIES.contains(effect.getPotion())) {
				event.setResult(Event.Result.DENY);
			}
		}

		@SubscribeEvent
		public static void onComfortApplied(PotionEvent.PotionAddedEvent event) {
			EffectInstance addedEffect = event.getPotionEffect();
			LivingEntity entity = event.getEntityLiving();
			if (addedEffect.getPotion().equals(ModEffects.COMFORT.get())) {
				for (Effect effect : COMFORT_IMMUNITIES) {
					entity.removePotionEffect(effect);
				}
			}
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}
}
