package seermod.actions;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class 灵感 extends AbstractGameAction {
    private final boolean upgraded;

    public 灵感(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.hand.size() == 1) {
                processCard(AbstractDungeon.player.hand.getTopCard());
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open("获得元素", 1, false, false, false, false);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                processCard(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }
        this.tickDuration();
    }

    private void processCard(AbstractCard card) {
        int pp = ExhaustiveField.ExhaustiveFields.exhaustive.get(card);
        if (pp > 0) {
            int elementsToGain = pp / 3;
            if (elementsToGain > 0) {
                addToTop(new 元素(elementsToGain, this.upgraded));
            }
        }
        AbstractDungeon.player.hand.moveToExhaustPile(card);
    }
} 